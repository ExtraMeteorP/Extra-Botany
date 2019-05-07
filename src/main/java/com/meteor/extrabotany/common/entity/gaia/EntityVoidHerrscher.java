package com.meteor.extrabotany.common.entity.gaia;

import com.google.common.base.Optional;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.entity.IBossProjectile;
import com.meteor.extrabotany.api.entity.IEntityWithShield;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.ModSounds;
import com.meteor.extrabotany.common.core.handler.PersistentVariableHandler;
import com.meteor.extrabotany.common.core.handler.PlayerStatHandler;
import com.meteor.extrabotany.common.entity.EntitySubspace;
import com.meteor.extrabotany.common.item.ItemMaterial;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.tool.ItemNatureOrb;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;
import vazkii.botania.api.boss.IBotaniaBoss;
import vazkii.botania.api.internal.ShaderCallback;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.PylonVariant;
import vazkii.botania.client.core.handler.BossBarHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntityVoidHerrscher extends EntityCreature implements IBotaniaBoss, IEntityWithShield, IEntityAdditionalSpawnData {

    public static final float ARENA_RANGE = 15F;
    private static final int SPAWN_TICKS = 160;
    private static final float MAX_HP = 300F;

    private static final String TAG_INVUL_TIME = "invulTime";
    private static final String TAG_AGGRO = "aggro";
    private static final String TAG_SOURCE_X = "sourceX";
    private static final String TAG_SOURCE_Y = "sourceY";
    private static final String TAG_SOURCE_Z = "sourcesZ";
    private static final String TAG_PLAYER_COUNT = "playerCount";
    private static final String TAG_RANKII = "rank2";
    private static final String TAG_RANKIII = "rank3";
    private static final String TAG_SHIELD = "shield";
    private static final String TAG_HARDCORE = "hardcore";
    private static final String TAG_SHIELDS = "shields";

    private static final DataParameter<Integer> INVUL_TIME = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SHIELD = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SHIELDS = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PLAYER_COUNT = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.VARINT);
    private static final DataParameter<BlockPos> SOURCE = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Optional<UUID>> BOSSINFO_ID = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<Boolean> RANKII = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RANKIII = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HARDCORE = EntityDataManager.createKey(EntityVoidHerrscher.class, DataSerializers.BOOLEAN);

    private static final BlockPos[] PYLON_LOCATIONS = {
            new BlockPos(4, 1, 4),
            new BlockPos(4, 1, -4),
            new BlockPos(-4, 1, 4),
            new BlockPos(-4, 1, -4)
    };

    private static final BlockPos[] MINION_LOCATIONS = {
            new BlockPos(3, 1, 3),
            new BlockPos(3, 1, -3),
            new BlockPos(-3, 1, 3),
            new BlockPos(-3, 1, -3)
    };

    private static final List<ResourceLocation> CHEATY_BLOCKS = Arrays.asList(
            new ResourceLocation("openblocks", "beartrap"),
            new ResourceLocation("thaumictinkerer", "magnet")
    );
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");
    ;
    public static int rot = -180;
    @SideOnly(Side.CLIENT)
    private static Rectangle barRect;
    @SideOnly(Side.CLIENT)
    private static Rectangle hpBarRect;
    public final List<UUID> playersWhoAttacked = new ArrayList<>();
    private final BossInfoServer bossInfo = (BossInfoServer) new BossInfoServer(new TextComponentTranslation("entity.extrabotany:voidherrscher.name"), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS).setCreateFog(true);
    public EntityPlayer trueKiller = null;
    private UUID bossInfoUUID = bossInfo.getUniqueId();
    private int playerCount = 0;
    private boolean hardMode = false;
    private BlockPos source = BlockPos.ORIGIN;
    private boolean aggro = false;
    private int tpDelay = 0;
    private int cd = 200;
    private int dodgecd = 300;
    private int skillType = 0;
    private int supportcd = 300;
    private List<String> contributorlist = PersistentVariableHandler.contributors;
    private boolean quickkill = true;
    @SideOnly(Side.CLIENT)
    private ShaderCallback shaderCallback;

    public EntityVoidHerrscher(World world) {
        super(world);
        setSize(0.6F, 1.8F);
        isImmuneToFire = true;
        experienceValue = 1225;
        if (world.isRemote) {
            Botania.proxy.addBoss(this);
        }
    }

    public static boolean spawn(EntityPlayer player, ItemStack stack, World world, BlockPos pos, boolean hard) {
        //initial checks
        if (
                !(world.getTileEntity(pos) instanceof TileEntityBeacon) ||
                        !isTruePlayer(player) ||
                        getGaiaGuardiansAround(world, pos) > 0
        )
            return false;

        //check difficulty
        if (world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            if (!world.isRemote)
                player.sendMessage(new TextComponentTranslation("botaniamisc.peacefulNoob").setStyle(new Style().setColor(TextFormatting.RED)));
            return false;
        }

        //check pylons
        for (BlockPos coords : PYLON_LOCATIONS) {
            BlockPos pos_ = pos.add(coords);

            IBlockState state = world.getBlockState(pos_);
            if (state.getBlock() != ModBlocks.pylon || state.getValue(BotaniaStateProps.PYLON_VARIANT) != PylonVariant.GAIA) {
                if (!world.isRemote)
                    player.sendMessage(new TextComponentTranslation("botaniamisc.needsCatalysts").setStyle(new Style().setColor(TextFormatting.RED)));
                return false;
            }
        }

        //check arena shape
        List<BlockPos> invalidArenaBlocks = checkArena(world, pos);
        if (!invalidArenaBlocks.isEmpty()) {
            if (world.isRemote) {
                Botania.proxy.setWispFXDepthTest(false);
                for (BlockPos pos_ : invalidArenaBlocks)
                    Botania.proxy.wispFX(pos_.getX() + 0.5, pos_.getY() + 0.5, pos_.getZ() + 0.5, 1F, 0.2F, 0.2F, 0.5F, 0F, 8);
                Botania.proxy.setWispFXDepthTest(true);
            } else {
                PacketHandler.sendTo((EntityPlayerMP) player,
                        new PacketBotaniaEffect(PacketBotaniaEffect.EffectType.ARENA_INDICATOR, pos.getX(), pos.getY(), pos.getZ()));

                player.sendMessage(new TextComponentTranslation("botaniamisc.badArena").setStyle(new Style().setColor(TextFormatting.RED)));
            }

            return false;
        }

        //all checks ok, spawn the boss
        if (!world.isRemote) {

            EntityVoidHerrscher e = new EntityVoidHerrscher(world);
            e.setPosition(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
            e.source = pos;
            e.setShield(5);
            e.hardMode = hard;

            int playerCount = e.getPlayersAround().size();
            e.playerCount = playerCount;
            e.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HP * playerCount);
            e.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).setBaseValue(15);

            e.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 10F, 0.1F);
            e.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(e)), null);
            world.spawnEntity(e);

            if (stack.getItem() instanceof ItemNatureOrb) {
                ItemNatureOrb o = (ItemNatureOrb) stack.getItem();
                o.addXP(stack, -100000);
            }
            if (stack.getItem() instanceof ItemMaterial) {
                stack.shrink(1);
            }
        }

        return true;
    }

    private static List<BlockPos> checkArena(World world, BlockPos beaconPos) {
        List<BlockPos> trippedPositions = new ArrayList<>();
        int range = (int) Math.ceil(ARENA_RANGE);
        BlockPos pos;

        for (int x = -range; x <= range; x++)
            for (int z = -range; z <= range; z++) {
                if (Math.abs(x) == 4 && Math.abs(z) == 4 || vazkii.botania.common.core.helper.MathHelper.pointDistancePlane(x, z, 0, 0) > ARENA_RANGE)
                    continue; // Ignore pylons and out of circle

                for (int y = -1; y <= 5; y++) {
                    if (x == 0 && y == 0 && z == 0)
                        continue; //this is the beacon

                    pos = beaconPos.add(x, y, z);

                    boolean expectedBlockHere = y == -1; //the floor
                    boolean isBlockHere = world.getBlockState(pos).getCollisionBoundingBox(world, pos) != null;

                    if (expectedBlockHere != isBlockHere) {
                        trippedPositions.add(pos);
                    }
                }
            }

        return trippedPositions;
    }

    public static boolean isTruePlayer(Entity e) {
        if (!(e instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) e;

        String name = player.getName();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }

    private static int getGaiaGuardiansAround(World world, BlockPos source) {
        float range = 15F;
        List l = world.getEntitiesWithinAABB(EntityVoidHerrscher.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
        return l.size();
    }

    private static boolean isCheatyBlock(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return CHEATY_BLOCKS.contains(Block.REGISTRY.getNameForObject(block));
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(Math.max(health, getHealth() - 8F));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world.getClosestPlayerToEntity(this, 6F) != null) {
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this, 16, 7,
                    new Vec3d(this.world.getClosestPlayerToEntity(this, 6F).posX, this.world.getClosestPlayerToEntity(this, 6F).posY, this.world.getClosestPlayerToEntity(this, 6F).posZ));
            if (vec3d != null)
                this.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, 1.2F);
        }

        if (this.ticksExisted > 3600)
            this.quickkill = false;

        int invul = getInvulTime();
        if (invul > 0) {
            setInvulTime(invul - 1);
            if (invul > 15) {
                motionX = 0;
                motionY = 0;
                motionZ = 0;
            }
        }

        for (EntityPlayer player : getPlayersAround()) {
            this.faceEntity(player, 360F, 360F);
            break;
        }

        if (this.getShields() > 2)
            for (Entity p : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.posX - 2.5F, this.posY - 2.5F, this.posZ - 2.5F, this.posX + 2.5F, this.posY + 2.5F, this.posZ + 2.5F))) {
                if (p instanceof IBossProjectile)
                    if (((IBossProjectile) p).isBoss(p))
                        continue;
                if (p instanceof IProjectile) {
                    ((IProjectile) p).shoot(-MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F),
                            -MathHelper.sin((this.rotationPitch) * 0.017453292F),
                            MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F),
                            1.5F, 0F);
                    p.getEntityData().setString("ownerName", this.getName());
                }
            }

        EntitySubspace sub = new EntitySubspace(world, this);
        sub.setLiveTicks(32);
        sub.setDelay(12);
        sub.posX = this.posX;
        sub.posY = this.posY + 1.9F;
        sub.posZ = this.posZ;
        sub.rotationYaw = this.rotationYaw;
        sub.setRotation(MathHelper.wrapDegrees(-this.rotationYaw + 180));
        sub.setType(1);
        sub.setSize(0.40F + world.rand.nextFloat() * 0.15F);
        if (!world.isRemote && (this.ticksExisted % 72 == 0 || this.dodgecd == 280))
            world.spawnEntity(sub);

        if (this.ticksExisted == 100) {
            EntitySubspaceLance lance = new EntitySubspaceLance(world, this);
            lance.setDamage(3);
            lance.setLife(4800);
            lance.setPitch(-90F);
            lance.setPosition(source.getX(), source.getY(), source.getZ());
            if (!world.isRemote)
                world.spawnEntity(lance);
        }

        for (EntityPlayer player : getPlayersAround())
            if (!playersWhoAttacked.contains(player.getUniqueID()))
                playersWhoAttacked.add(player.getUniqueID());

        if (!getRankII() && (getHealth() <= getMaxHealth() * 0.80F || getHardcore())) {
            setRankII(true);
            setShield(5);
            spawnDivineJudge();
            setShields(1);
            for (int i = 0; i < 2; i++) {
                spawnSubspaceLanceRandomly();
            }
        }

        if (!getRankIII() && (getHealth() <= getMaxHealth() * 0.40F || getHardcore() && getHealth() <= getMaxHealth() * 0.6F)) {
            setRankIII(true);
            setShields(3);
            for (int i = 0; i < 4; i++) {
                spawnSubspaceLanceRandomly();
            }
            if (playersWhoAttacked.size() > 0) {
                this.heal(playersWhoAttacked.size() * 150F);
            }
        }

        BlockPos source = getSource();

        if (getRankII()) {
            if (this.posY < source.getY() + 2F)
                this.motionY = 0.1F;
        }

        if (world.isRemote) {
            particles();
            EntityPlayer player = Botania.proxy.getClientPlayer();
            if (getPlayersAround().contains(player))
                player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
            return;
        }

        bossInfo.setPercent(getHealth() / getMaxHealth());

        if (isRiding())
            dismountRidingEntity();

        if (world.getDifficulty() == EnumDifficulty.PEACEFUL)
            setDead();

        smashCheatyBlocks();

        List<EntityPlayer> players = getPlayersAround();

        if (players.isEmpty() && !world.playerEntities.isEmpty())
            setDead();
        else {
            for (EntityPlayer player : players) {
                clearPotions(player);
                keepInsideArena(player);
                player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
                if (this.supportcd == 0)
                    support(player);
            }
        }

        if (isDead)
            return;

        if (getRankIII()) {
            if (this.supportcd > 0)
                this.supportcd--;
            if (ticksExisted % 95 == 0) {
                for (int t = 0; t < 2; t++)
                    spawnMissile(2);
                heal(1F);
            }
            if (cd == 0 && skillType == 1) {
                spawnVoidJudge();
                cd = 240;
                skillType = 2;
            }
        }

        if (getRankII())
            if (ticksExisted % 115 == 0)
                spawnMissile(1);

        if (ticksExisted > 60 && ticksExisted % 75 == 0) {
            spawnMissile(0);
            if (world.rand.nextInt(10) < 3)
                spawnMissile(1);
        }

        int base = getHardcore() ? 9 + playerCount * 3 : 7 + playerCount * 3;
        int count = getRankIII() ? base + 6 : getRankII() ? base + 2 : base;
        if (ticksExisted > 200 && ticksExisted % (getRankIII() ? 230 : getRankII() ? 270 : 310) == 0)
            for (int i = 0; i < count; i++) {
                int x = source.getX() - 10 + rand.nextInt(20);
                int z = source.getZ() - 10 + rand.nextInt(20);
                int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, -1, z)).getY();

                EntitySkullLandmine landmine = new EntitySkullLandmine(world);
                if (i % 8 == 0)
                    landmine.setType(2);
                if (i % 6 == 0)
                    landmine.setType(1);
                landmine.setPosition(x + 0.5, y, z + 0.5);
                landmine.summoner = this;
                world.spawnEntity(landmine);
            }

        for (EntityPlayer player : getPlayersAround()) {
            if (!player.isCreative()) {
                if (ConfigHandler.GAIA_DISARM)
                    disarm(player);

            }
            if (player.motionY > 0)
                player.motionY = -player.motionY;
            if (player.isRiding())
                player.dismountRidingEntity();
        }

        if (cd > 0 && getRankII())
            cd--;

        if (cd == 250 && skillType == 1)
            for (EntityPlayer player : getPlayersAround())
                if (world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaPreparing", I18n.format("entity.extrabotany:voidherrscher.name")).setStyle(new Style().setColor(TextFormatting.WHITE)));

        if (cd == 100 && skillType == 1)
            for (EntityPlayer player : getPlayersAround())
                if (world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning", I18n.format("entity.extrabotany:voidherrscher.name")).setStyle(new Style().setColor(TextFormatting.RED)));

        if (cd == 100 && skillType == 0)
            for (EntityPlayer player : getPlayersAround())
                if (world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning2", I18n.format("entity.extrabotany:voidherrscher.name")).setStyle(new Style().setColor(TextFormatting.RED)));

        if (cd == 0 && !world.isRemote && skillType == 0 && !getPlayersAround().isEmpty()) {
            EntityPlayer player = getPlayersAround().get(world.rand.nextInt(getPlayersAround().size()));
            player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning3", I18n.format("entity.extrabotany:voidherrscher.name")).setStyle(new Style().setColor(TextFormatting.RED)));
            ExtraBotanyAPI.dealTrueDamage(player, player.getMaxHealth() * 0.20F + 6);
            spawnSubspaceLance(player.getPosition());
            cd = 290;
            skillType = getRankIII() ? 1 : world.rand.nextInt(2);
        }

        if (cd == 0 && !world.isRemote && skillType == 2) {
            if (ConfigHandler.GAIA_DIVINEJUDGE)
                spawnDivineJudge();
            else spawnSubspaceLanceRandomly();
            cd = 350;
            skillType = getRankIII() ? 3 : 0;
        }

        if (cd == 0 && !world.isRemote && skillType == 3) {
            spawnSubspaceLanceRandomly();
            cd = 200;
            skillType = world.rand.nextInt(1);
        }

        if (tpDelay > 0)
            tpDelay--;

        if (tpDelay == 0 && getHealth() > 0) {
            teleportRandomly();
            tpDelay = getRankIII() ? 75 : 85;
        }

        if (dodgecd > 0)
            dodgecd--;

    }

    private void support(EntityPlayer player) {
        if (!contributorlist.isEmpty()) {
            int index = world.rand.nextInt(contributorlist.size());
            String contributor = contributorlist.get(index);
            this.supportcd = 400;
            int rand = this.world.rand.nextInt(7);
            int r = 0;
            switch (rand) {
                case 0:
                    r = this.world.rand.nextInt(2);
                    player.heal(player.getMaxHealth() * 0.25F);
                    break;
                case 1:
                    this.attackEntityFrom(DamageSource.causePlayerDamage(player), 15F);
                    break;
                case 2:
                    this.tpDelay += 200;
                    break;
                case 3:
                    r = this.world.rand.nextInt(2);
                    this.cd += 120;
                    break;
                case 4:
                    r = this.world.rand.nextInt(3);
                    this.supportcd = 200;
                    break;
                case 5:
                    r = this.world.rand.nextInt(2);
                    player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 300, 5));
                    break;
                case 6:
                    r = this.world.rand.nextInt(3);
                    for (int i = 0; i < 2; i++) {
                        ItemStack stack = new ItemStack(ModItems.candy, 1, r);
                        stack.onItemUseFinish(this.world, player);
                    }
                    break;
                default:
                    break;
            }
            player.sendMessage(new TextComponentTranslation("extrabotanymisc.support" + String.valueOf(rand) + String.valueOf(r), contributor).setStyle(new Style().setColor(TextFormatting.AQUA)));
            contributorlist.remove(index);
        }
    }

    private void disarm(EntityPlayer player) {
        if (!match(player.getHeldItemMainhand())) {
            EntityItem item = player.dropItem(true);
            item.setPickupDelay(90);
        }
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stackAt = player.inventory.getStackInSlot(i);
            if (!match(stackAt)) {
                if (!stackAt.isEmpty()) {
                    EntityItem item = player.entityDropItem(stackAt, 0);
                    item.setPickupDelay(90);
                }
                player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
        }
    }

    private boolean match(ItemStack stack) {
        String m = stack.getItem().getRegistryName().toString();
        if (m.indexOf("botania") != -1 || m.indexOf("extrabotany") != -1 || m.indexOf("minecraft") != -1)
            return true;
        return false;
    }

    private boolean match(Block block) {
        String m = Block.REGISTRY.getNameForObject(block).toString();
        if (m.indexOf("botania") != -1 || m.indexOf("extrabotany") != -1 || m.indexOf("minecraft") != -1)
            return true;
        return false;
    }

    private void spawnSubspaceLanceRandomly() {
        double newX = source.getX() + (rand.nextDouble() - .5) * ARENA_RANGE;
        double newY = getRankII() ? source.getY() + 2F : source.getY();
        double newZ = source.getZ() + (rand.nextDouble() - .5) * ARENA_RANGE;
        spawnSubspaceLance(new BlockPos(newX, newY, newZ));
    }

    private void spawnSubspaceLance(BlockPos pos) {
        EntitySubspaceLance lance = new EntitySubspaceLance(world, this);
        lance.setDamage(3);
        lance.setLife(1200);
        lance.setPitch(-90F);
        lance.setPosition(pos.getX(), pos.getY() + 12F, pos.getZ());
        if (!world.isRemote)
            world.spawnEntity(lance);
    }

    private void spawnVoidJudge() {
        this.setInvulTime(120);
        this.setPositionAndUpdate(getSource().getX(), getSource().getY() + 2F, getSource().getZ());
        for (int i = 0; i < 24; i++) {

            Vector3 look = new Vector3(this.getLookVec()).multiply(1, 0, 1);

            double playerRot = Math.toRadians(this.rotationYaw + 90);
            if (look.x == 0 && look.z == 0)
                look = new Vector3(Math.cos(playerRot), 0, Math.sin(playerRot));

            look = look.normalize().multiply(-2);

            int div = i / 8;
            int mod = i % 8;

            Vector3 pl = look.add(Vector3.fromEntityCenter(this)).add(0, 1.6, div * 0.1);

            Random rand = this.world.rand;
            Vector3 axis = look.normalize().crossProduct(new Vector3(-1, 0, -1)).normalize();

            double rot = mod * Math.PI / 7 - Math.PI / 2;

            Vector3 axis1 = axis.multiply(div * 3.5 + 5).rotate(rot, look);
            if (axis1.y < 0)
                axis1 = axis1.multiply(1, -1, 1);

            Vector3 end = pl.add(axis1);

            if (!this.getPlayersAround().isEmpty()) {
                EntitySubspace sub = new EntitySubspace(world, this);
                sub.setLiveTicks(120);
                sub.setDelay(15 + world.rand.nextInt(12));
                sub.posX = end.x;
                sub.posY = end.y - 0.5F + world.rand.nextFloat();
                sub.posZ = end.z;
                sub.rotationYaw = this.rotationYaw;
                sub.setRotation(MathHelper.wrapDegrees(-this.rotationYaw + 180));
                sub.setInterval(10 + world.rand.nextInt(10));
                sub.setSize(3.0F + world.rand.nextFloat() * 0.5F);
                sub.setType(2);
                if (!world.isRemote)
                    world.spawnEntity(sub);
                if (i == 1)
                    sub.playSound(ModSounds.spearsubspace, 1F, 1F);
            }
        }
    }

    private void spawnDivineJudge() {
        for (int i = 0; i < 8; i++) {
            float rad = i * 45 * (float) Math.PI / 180F;
            double x = getSource().getX() + 0.5 - Math.cos(rad) * 5;
            double y = getSource().getY() + 7;
            double z = getSource().getZ() + 0.5 - Math.sin(rad) * 5;
            EntitySwordDomain domain = new EntitySwordDomain(this.getEntityWorld());
            EntityDomain d = new EntityDomain(this.getEntityWorld());
            d.setPosition(x, y - 0.5F, z);
            this.getEntityWorld().spawnEntity(d);
            if (!playersWhoAttacked.isEmpty())
                domain.setUUID(playersWhoAttacked.get(Math.min(i, Math.max(0, playersWhoAttacked.size() - 1))));
            domain.setType(i);
            domain.setPosition(x, y, z + 2);
            domain.setCount((int) (y - 2));
            domain.setSource(getSource());
            this.getEntityWorld().spawnEntity(domain);
        }
    }

    private void spawnMissile(int type) {
        EntitySkullMissile missile = new EntitySkullMissile(this);
        missile.setPosition(posX + (Math.random() - 0.5 * 0.1), posY + 1.8 + (Math.random() - 0.5 * 0.1), posZ + (Math.random() - 0.5 * 0.1));
        missile.setDamage(getHardcore() ? 6 : 4);
        if (type > 0) {
            missile.setFire(true);
        }
        if (type > 1) {
            missile.setEffect(true);
            missile.setTrueDamage(getHardcore() ? 3 : 2);
        }
        if (missile.findTarget()) {
            if (type > 2) {
                int x = getSource().getX() - 10 + rand.nextInt(20);
                int z = getSource().getZ() - 10 + rand.nextInt(20);
                missile.setPosition(x, posY + 1.8 + (Math.random() - 0.5 * 0.1), z);
            }
            playSound(vazkii.botania.common.core.handler.ModSounds.missile, 0.6F, 0.8F + (float) Math.random() * 0.2F);
            if (!world.isRemote)
                world.spawnEntity(missile);
        }
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, ARENA_RANGE * 1.5F));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(INVUL_TIME, 0);
        dataManager.register(RANKII, false);
        dataManager.register(RANKIII, false);
        dataManager.register(HARDCORE, false);
        dataManager.register(SHIELD, 0);
        dataManager.register(SHIELDS, 0);
    }

    public int getShields() {
        return dataManager.get(SHIELDS);
    }

    public void setShields(int s) {
        dataManager.set(SHIELDS, s);
    }

    public boolean getHardcore() {
        return dataManager.get(HARDCORE);
    }

    public void setHardcore(boolean b) {
        dataManager.set(HARDCORE, b);
    }

    public boolean getRankII() {
        return dataManager.get(RANKII);
    }

    public void setRankII(boolean b) {
        dataManager.set(RANKII, b);
    }

    public boolean getRankIII() {
        return dataManager.get(RANKIII);
    }

    public void setRankIII(boolean b) {
        dataManager.set(RANKIII, b);
    }

    public int getInvulTime() {
        return dataManager.get(INVUL_TIME);
    }

    public void setInvulTime(int time) {
        dataManager.set(INVUL_TIME, time);
    }

    public BlockPos getSource() {
        return source;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound cmp) {
        super.writeEntityToNBT(cmp);
        cmp.setInteger(TAG_INVUL_TIME, getInvulTime());
        cmp.setBoolean(TAG_AGGRO, aggro);

        cmp.setInteger(TAG_SOURCE_X, source.getX());
        cmp.setInteger(TAG_SOURCE_Y, source.getY());
        cmp.setInteger(TAG_SOURCE_Z, source.getZ());

        cmp.setInteger(TAG_PLAYER_COUNT, playerCount);
        cmp.setInteger(TAG_SHIELD, getShield());
        cmp.setBoolean(TAG_RANKII, getRankII());
        cmp.setBoolean(TAG_RANKIII, getRankIII());
        cmp.setInteger(TAG_SHIELDS, getShields());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound cmp) {
        super.readEntityFromNBT(cmp);
        setInvulTime(cmp.getInteger(TAG_INVUL_TIME));
        aggro = cmp.getBoolean(TAG_AGGRO);

        int x = cmp.getInteger(TAG_SOURCE_X);
        int y = cmp.getInteger(TAG_SOURCE_Y);
        int z = cmp.getInteger(TAG_SOURCE_Z);
        source = new BlockPos(x, y, z);

        if (cmp.hasKey(TAG_PLAYER_COUNT))
            playerCount = cmp.getInteger(TAG_PLAYER_COUNT);
        else playerCount = 1;

        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
        setShields(cmp.getInteger(TAG_SHIELDS));
    }

    @Override
    public void setCustomNameTag(@Nonnull String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void onKillCommand() {
        this.setHealth(0.0F);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float par2) {
        Entity e = source.getTrueSource();

        List<DamageSource> sourcelist = new ArrayList();
        sourcelist.add(DamageSource.ANVIL);
        sourcelist.add(DamageSource.CACTUS);
        sourcelist.add(DamageSource.DROWN);
        sourcelist.add(DamageSource.FALL);
        sourcelist.add(DamageSource.FALLING_BLOCK);
        sourcelist.add(DamageSource.IN_FIRE);
        sourcelist.add(DamageSource.LAVA);
        sourcelist.add(DamageSource.ON_FIRE);
        sourcelist.add(DamageSource.LIGHTNING_BOLT);
        sourcelist.add(DamageSource.MAGIC);
        sourcelist.add(DamageSource.OUT_OF_WORLD);
        sourcelist.add(DamageSource.WITHER);
        if (sourcelist.contains(source) || this.getInvulTime() > 0)
            return false;

        if (e instanceof EntityPlayer && isTruePlayer(e)) {
            EntityPlayer player = (EntityPlayer) e;

            if (!playersWhoAttacked.contains(player.getUniqueID()))
                playersWhoAttacked.add(player.getUniqueID());

            if (vazkii.botania.common.core.helper.MathHelper.pointDistancePlane(player.posX, player.posZ, getSource().getX(), getSource().getZ()) > ARENA_RANGE)
                player.attemptTeleport(getSource().getX(), getSource().getY(), getSource().getZ());

            int cap = 20;

            this.setInvulTime(this.getInvulTime() + 10);

            if (getRankII())
                teleportRandomly();

            if (this.cd > 120)
                this.cd -= 20;

            if (dodgecd == 0) {
                EntityVoid vo = new EntityVoid(this);
                vo.setPosition(player.posX, player.posY + 0.7F, player.posZ);
                world.spawnEntity(vo);
                dodgecd = 300;
                if (Math.random() < 0.2F)
                    spawnSubspaceLance(player.getPosition());
                return false;
            }

            if (getShields() > 0) {
                for (PotionEffect pe : player.getActivePotionEffects()) {
                    this.addPotionEffect(pe);
                }
                for (int i = 0; i < getShields(); i++) {
                    par2 *= 0.85F;
                    cap *= 0.85F;
                }

                if (par2 > 20 && !this.world.isRemote) {
                    setShields(getShields() - 1);
                    dodgecd = 0;
                }
            }

            return super.attackEntityFrom(source, Math.min(cap, par2));
        }

        return false;
    }

    @Override
    protected void damageEntity(@Nonnull DamageSource par1DamageSource, float par2) {
        super.damageEntity(par1DamageSource, par2);

        Entity attacker = par1DamageSource.getImmediateSource();
        if (attacker != null) {
            Vector3 thisVector = Vector3.fromEntityCenter(this);
            Vector3 playerVector = Vector3.fromEntityCenter(attacker);
            Vector3 motionVector = thisVector.subtract(playerVector).normalize().multiply(0.75);

            if (getHealth() > 0) {
                motionX = -motionVector.x;
                motionY = 0.5;
                motionZ = -motionVector.z;
                tpDelay = 4;
            }
        }
    }

    @Override
    public void onDeath(@Nonnull DamageSource source) {
        super.onDeath(source);
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 20F, (1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY, posZ, 1D, 0D, 0D);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HP);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public ResourceLocation getLootTable() {
        return new ResourceLocation(LibMisc.MOD_ID, "gaia_guardian_3");
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, @Nonnull DamageSource source) {
        // Save true killer, they get extra loot
        if ("player".equals(source.getDamageType())
                && source.getTrueSource() instanceof EntityPlayer) {
            trueKiller = (EntityPlayer) source.getTrueSource();
        }

        // Drop equipment and clear it so multiple calls to super don't do it again
        super.dropEquipment(wasRecentlyHit, lootingModifier);

        for (EntityEquipmentSlot e : EntityEquipmentSlot.values()) {
            setItemStackToSlot(e, ItemStack.EMPTY);
        }

        // Generate loot table for every single attacking player
        for (UUID u : playersWhoAttacked) {
            EntityPlayer player = world.getPlayerEntityByUUID(u);
            if (player == null)
                continue;

            EntityPlayer saveLastAttacker = attackingPlayer;
            double savePosX = posX;
            double savePosY = posY;
            double savePosZ = posZ;

            attackingPlayer = player; // Fake attacking player as the killer
            posX = player.posX;       // Spoof pos so drops spawn at the player
            posY = player.posY;
            posZ = player.posZ;
            super.dropLoot(wasRecentlyHit, lootingModifier, DamageSource.causePlayerDamage(player));
            ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.HERRSCHER_DEFEAT);
            if (this.quickkill)
                ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ENDGAME_GOAL);
            PlayerStatHandler.setHerrscherDefeat(player, PlayerStatHandler.getVoidHerrscherDefeat(player) + 1);
            posX = savePosX;
            posY = savePosY;
            posZ = savePosZ;
            attackingPlayer = saveLastAttacker;
        }

        trueKiller = null;
    }

    @Override
    public void setDead() {
        if (world.isRemote) {
            Botania.proxy.removeBoss(this);
        }
        world.playEvent(1010, getSource(), 0);
        super.setDead();
    }

    public List<EntityPlayer> getPlayersAround() {
        BlockPos source = getSource();
        float range = 15F;
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    private void particles() {
        BlockPos source = getSource();

        for (int i = 0; i < 360; i += 12) {
            float r = 0.3F;
            float g = 0F;
            float b = 1F;
            float m = 0.15F;
            float mv = 0.35F;

            float rad = i * (float) Math.PI / 180F;
            double x = source.getX() + 0.5 - Math.cos(rad) * ARENA_RANGE;
            double y = source.getY() + 0.5;
            double z = source.getZ() + 0.5 - Math.sin(rad) * ARENA_RANGE;
            if (this.ticksExisted % 2 == 0)
                Botania.proxy.wispFX(x, y, z, r, g, b, 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * mv, (float) (Math.random() - 0.5F) * m);
        }

        if (getInvulTime() > 10) {
            Vector3 pos = Vector3.fromEntityCenter(this).subtract(new Vector3(0, 0.2, 0));
            for (BlockPos arr : PYLON_LOCATIONS) {
                Vector3 pylonPos = new Vector3(source.getX() + arr.getX(), source.getY() + arr.getY(), source.getZ() + arr.getZ());
                double worldTime = ticksExisted;
                worldTime /= 5;

                float rad = 0.75F + (float) Math.random() * 0.05F;
                double xp = pylonPos.x + 0.5 + Math.cos(worldTime) * rad;
                double zp = pylonPos.z + 0.5 + Math.sin(worldTime) * rad;

                Vector3 partPos = new Vector3(xp, pylonPos.y, zp);
                Vector3 mot = pos.subtract(partPos).multiply(0.04);

                float r = 0F + (float) Math.random() * 0.3F;
                float g = (float) Math.random() * 0.3F;
                float b = 0.7F + (float) Math.random() * 0.3F;

                Botania.proxy.wispFX(partPos.x, partPos.y, partPos.z, r, g, b, 0.25F + (float) Math.random() * 0.1F, -0.075F - (float) Math.random() * 0.015F);
                Botania.proxy.wispFX(partPos.x, partPos.y, partPos.z, r, g, b, 0.4F, (float) mot.x, (float) mot.y, (float) mot.z);
            }
        }
    }

    private void smashCheatyBlocks() {
        int radius = 10;
        int posXInt = MathHelper.floor(getSource().getX());
        int posYInt = MathHelper.floor(getSource().getY());
        int posZInt = MathHelper.floor(getSource().getZ());
        for (int i = -radius; i < radius + 1; i++)
            for (int j = -radius; j < radius + 1; j++)
                for (int k = -radius; k < radius + 1; k++) {
                    int xp = posXInt + i;
                    int yp = posYInt + j;
                    int zp = posZInt + k;
                    BlockPos posp = new BlockPos(xp, yp, zp);
                    if (isCheatyBlock(world, posp) || world.getBlockState(posp).getMaterial() == Material.WATER || world.getBlockState(posp).getMaterial() == Material.LAVA || (ConfigHandler.GAIA_SMASH && !match(world.getBlockState(posp).getBlock()))) {
                        world.destroyBlock(posp, true);
                    }
                }
    }

    private void clearPotions(EntityPlayer player) {
        int posXInt = MathHelper.floor(posX);
        int posZInt = MathHelper.floor(posZ);

        List<Potion> potionsToRemove = player.getActivePotionEffects().stream()
                .filter(effect -> effect.getDuration() < 160 && effect.getIsAmbient() && !effect.getPotion().isBadEffect())
                .map(PotionEffect::getPotion)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            player.removePotionEffect(potion);
            ((WorldServer) world).getPlayerChunkMap().getEntry(posXInt >> 4, posZInt >> 4).sendPacket(new SPacketRemoveEntityEffect(player.getEntityId(), potion));
        });
    }

    private void keepInsideArena(EntityPlayer player) {
        BlockPos source = getSource();
        if (vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(player.posX, player.posY, player.posZ, source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5) >= ARENA_RANGE) {
            Vector3 sourceVector = new Vector3(source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5);
            Vector3 playerVector = Vector3.fromEntityCenter(player);
            Vector3 motion = sourceVector.subtract(playerVector).normalize();

            player.motionX = motion.x;
            player.motionY = 0.2;
            player.motionZ = motion.z;
            player.velocityChanged = true;
            player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 400, 4));
        }
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    protected void collideWithNearbyEntities() {
        if (getInvulTime() == 0)
            super.collideWithNearbyEntities();
    }

    @Override
    public boolean canBePushed() {
        return super.canBePushed() && getInvulTime() == 0;
    }

    private void teleportRandomly() {
        //choose a location to teleport to
        if (this.getInvulTime() > 10)
            return;

        double oldX = posX, oldY = posY, oldZ = posZ;
        double newX, newY, newZ;
        int tries = 0;

        do {
            newX = source.getX() + (rand.nextDouble() - .5) * ARENA_RANGE;
            newY = getRankII() ? source.getY() + 2F : source.getY();
            newZ = source.getZ() + (rand.nextDouble() - .5) * ARENA_RANGE;
            tries++;
            //ensure it's inside the arena ring, and not just its bounding square
        }
        while (tries < 50 && vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(newX, newY, newZ, source.getX(), source.getY(), source.getZ()) > 12);

        if (tries == 50) {
            //failsafe: teleport to the beacon
            newX = source.getX() + 0.5F;
            newY = source.getY() + 2;
            newZ = source.getZ() + 0.5F;
        }

        //teleport there
        setPositionAndUpdate(newX, newY, newZ);

        //play sound
        world.playSound(null, oldX, oldY, oldZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
        this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);

        Random random = getRNG();

        //spawn particles along the path
        int particleCount = 128;
        for (int i = 0; i < particleCount; ++i) {
            double progress = i / (double) (particleCount - 1);
            float vx = (random.nextFloat() - 0.5F) * 0.2F;
            float vy = (random.nextFloat() - 0.5F) * 0.2F;
            float vz = (random.nextFloat() - 0.5F) * 0.2F;
            double px = oldX + (newX - oldX) * progress + (random.nextDouble() - 0.5D) * width * 2.0D;
            double py = oldY + (newY - oldY) * progress + random.nextDouble() * height;
            double pz = oldZ + (newZ - oldZ) * progress + (random.nextDouble() - 0.5D) * width * 2.0D;
            world.spawnParticle(EnumParticleTypes.PORTAL, px, py, pz, vx, vy, vz);
        }

        Vec3d oldPosVec = new Vec3d(oldX, oldY + height / 2, oldZ);
        Vec3d newPosVec = new Vec3d(newX, newY + height / 2, newZ);

        if (oldPosVec.squareDistanceTo(newPosVec) > 1) {
            //damage players in the path of the teleport
            for (EntityPlayer player : getPlayersAround()) {
                RayTraceResult rtr = player.getEntityBoundingBox().grow(0.25).calculateIntercept(oldPosVec, newPosVec);
                if (rtr != null)
                    player.attackEntityFrom(DamageSource.causeMobDamage(this), 6);
            }

            //break blocks in the path of the teleport
            int breakSteps = (int) oldPosVec.distanceTo(newPosVec);
            if (breakSteps >= 2) {
                for (int i = 0; i < breakSteps; i++) {
                    float progress = i / (float) (breakSteps - 1);
                    int breakX = MathHelper.floor(oldX + (newX - oldX) * progress);
                    int breakY = MathHelper.floor(oldY + (newY - oldY) * progress);
                    int breakZ = MathHelper.floor(oldZ + (newZ - oldZ) * progress);

                    if (ConfigHandler.GAIA_SMASH)
                        smashBlocksAround(breakX, breakY, breakZ, 1);
                }
            }
        }
    }

    private void smashBlocksAround(int centerX, int centerY, int centerZ, int radius) {
        for (int dx = -radius; dx <= radius; dx++)
            for (int dy = -radius; dy <= radius + 1; dy++)
                for (int dz = -radius; dz <= radius; dz++) {
                    int x = centerX + dx;
                    int y = centerY + dy;
                    int z = centerZ + dz;

                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();

                    if (state.getBlockHardness(world, pos) == -1) continue;

                    if (CHEATY_BLOCKS.contains(block.getRegistryName())) {
                        world.destroyBlock(pos, true);
                    } else {
                        //don't break blacklisted blocks
                        if (ExtraBotanyAPI.gaiaBreakBlacklist.contains(block)) continue;
                        //don't break the floor
                        if (y == source.getY() - 1) continue;
                        //don't break blocks in pylon columns
                        if (Math.abs(source.getX() - x) == 4 && Math.abs(source.getZ() - z) == 4) continue;

                        world.destroyBlock(pos, true);
                    }
                }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getBossBarTexture() {
        return BossBarHandler.defaultBossBar;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Rectangle getBossBarTextureRect() {
        if (barRect == null)
            barRect = new Rectangle(0, 0, 185, 15);
        return barRect;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Rectangle getBossBarHPTextureRect() {
        if (hpBarRect == null)
            hpBarRect = new Rectangle(0, barRect.y + barRect.height, 181, 7);
        return hpBarRect;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int bossBarRenderCallback(ScaledResolution res, int x, int y) {
        GlStateManager.pushMatrix();
        int px = x + 160;
        int py = y + 12;

        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = new ItemStack(Items.SKULL, 1, 3);
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        mc.getRenderItem().renderItemIntoGUI(stack, px, py);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        boolean unicode = mc.fontRenderer.getUnicodeFlag();
        mc.fontRenderer.setUnicodeFlag(true);
        mc.fontRenderer.drawStringWithShadow("" + playerCount, px + 15, py + 4, 0xFFFFFF);
        mc.fontRenderer.setUnicodeFlag(unicode);
        GlStateManager.popMatrix();

        return 5;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBossBarShaderProgram(boolean background) {
        return background ? 0 : ShaderHelper.dopplegangerBar;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ShaderCallback getBossBarShaderCallback(boolean background, int shader) {
        if (shaderCallback == null)
            shaderCallback = shader1 -> {
                int grainIntensityUniform = ARBShaderObjects.glGetUniformLocationARB(shader1, "grainIntensity");
                int hpFractUniform = ARBShaderObjects.glGetUniformLocationARB(shader1, "hpFract");

                float time = getInvulTime();
                float grainIntensity = time > 20 ? 1F : Math.max(0F, time / 20F);

                ARBShaderObjects.glUniform1fARB(grainIntensityUniform, grainIntensity);
                ARBShaderObjects.glUniform1fARB(hpFractUniform, getHealth() / getMaxHealth());
            };

        return background ? null : shaderCallback;
    }

    @Override
    public int getShield() {
        return dataManager.get(SHIELD);
    }

    @Override
    public void setShield(int shield) {
        dataManager.set(SHIELD, shield);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(playerCount);
        buffer.writeBoolean(hardMode);
        buffer.writeLong(source.toLong());
        buffer.writeLong(bossInfoUUID.getMostSignificantBits());
        buffer.writeLong(bossInfoUUID.getLeastSignificantBits());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void readSpawnData(ByteBuf additionalData) {
        playerCount = additionalData.readInt();
        hardMode = additionalData.readBoolean();
        source = BlockPos.fromLong(additionalData.readLong());
        long msb = additionalData.readLong();
        long lsb = additionalData.readLong();
        bossInfoUUID = new UUID(msb, lsb);
        Minecraft.getMinecraft().getSoundHandler().playSound(new DopplegangerMusic(this));
    }

    @Override
    public UUID getBossInfoUuid() {
        return bossInfoUUID;
    }

    @SideOnly(Side.CLIENT)
    private static class DopplegangerMusic extends MovingSound {
        private final EntityVoidHerrscher guardian;

        public DopplegangerMusic(EntityVoidHerrscher guardian) {
            super(com.meteor.extrabotany.common.core.handler.ModSounds.herrscherMusic, SoundCategory.RECORDS);
            this.guardian = guardian;
            this.xPosF = guardian.getSource().getX();
            this.yPosF = guardian.getSource().getY();
            this.zPosF = guardian.getSource().getZ();
            this.repeat = true;
        }

        @Override
        public void update() {
            if (!guardian.isEntityAlive()) {
                donePlaying = true;
            }
        }
    }

}
