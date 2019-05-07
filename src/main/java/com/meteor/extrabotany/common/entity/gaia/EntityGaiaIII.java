package com.meteor.extrabotany.common.entity.gaia;

import com.google.common.base.Optional;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.entity.IEntityWithShield;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.PlayerStatHandler;
import com.meteor.extrabotany.common.core.handler.StatHandler;
import com.meteor.extrabotany.common.item.ItemMaterial;
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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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
import vazkii.botania.common.lib.LibEntityNames;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntityGaiaIII extends EntityLiving implements IBotaniaBoss, IEntityWithShield, IEntityAdditionalSpawnData {

    public static final float ARENA_RANGE = 12F;
    private static final int SPAWN_TICKS = 160;
    private static final float MAX_HP = 450F;

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

    private static final DataParameter<Integer> INVUL_TIME = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SHIELD = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PLAYER_COUNT = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.VARINT);
    private static final DataParameter<BlockPos> SOURCE = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Optional<UUID>> BOSSINFO_ID = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<Boolean> RANKII = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RANKIII = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HARDCORE = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BOOLEAN);

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
    @SideOnly(Side.CLIENT)
    private static Rectangle barRect;
    @SideOnly(Side.CLIENT)
    private static Rectangle hpBarRect;
    public final List<UUID> playersWhoAttacked = new ArrayList<>();
    private final BossInfoServer bossInfo = (BossInfoServer) new BossInfoServer(new TextComponentTranslation("entity." + LibEntityNames.DOPPLEGANGER_REGISTRY + ".name"), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS).setCreateFog(true);
    public EntityPlayer trueKiller = null;
    private UUID bossInfoUUID = bossInfo.getUniqueId();
    private int playerCount = 0;
    private boolean hardMode = false;
    private BlockPos source = BlockPos.ORIGIN;
    private boolean aggro = false;
    private int tpDelay = 0;
    private int cd = 200;
    private int skillType = 0;
    @SideOnly(Side.CLIENT)
    private ShaderCallback shaderCallback;

    public EntityGaiaIII(World world) {
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

            EntityGaiaIII e = new EntityGaiaIII(world);
            e.setPosition(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
            e.source = pos;
            e.setShield(5);
            e.hardMode = hard;
            e.setCustomNameTag(player.getGameProfile().getName());
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
        List l = world.getEntitiesWithinAABB(EntityGaiaIII.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
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

        for (EntityPlayer player : getPlayersAround()) {
            this.faceEntity(player, 360F, 360F);
            break;
        }

        for (EntityPlayer player : getPlayersAround())
            if (!playersWhoAttacked.contains(player.getUniqueID()))
                playersWhoAttacked.add(player.getUniqueID());

        if (!getRankII() && (getHealth() <= getMaxHealth() * 0.75F || getHardcore())) {
            setRankII(true);
            setShield(getHardcore() ? 10 : 5);
            spawnMinion();
            spawnDivineJudge();
            for (EntityPlayer player : getPlayersAround())
                if (!world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.minionSpawn").setStyle(new Style().setColor(TextFormatting.WHITE)));
        }
        if (!getRankIII() && (getHealth() <= getMaxHealth() * 0.4F || getHardcore() && getHealth() <= getMaxHealth() * 0.6F)) {
            setRankIII(true);
            setShield(getHardcore() ? 10 : 5);
            spawnMinion();
            spawnDivineJudge();
            for (EntityPlayer player : getPlayersAround())
                if (!world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.minionSpawn").setStyle(new Style().setColor(TextFormatting.WHITE)));
        }

        BlockPos source = getSource();

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
            }
        }

        if (isDead)
            return;


        if (getRankIII()) {
            if (ticksExisted % 65 == 0) {
                for (int t = 0; t < 2; t++)
                    spawnMissile(2);
                heal(1F);
            }
            if (cd == 0 && skillType == 1) {
                for (int t = 0; t < 17 + playerCount * 4; t++)
                    spawnMissile(3);
                cd = 200;
                skillType = 2;
            }
        }

        if (getRankII())
            if (ticksExisted % 55 == 0)
                spawnMissile(1);

        if (ticksExisted > 60 && ticksExisted % 40 == 0) {
            spawnMissile(0);
            if (world.rand.nextInt(10) < 4)
                spawnMissile(1);
        }

        int base = getHardcore() ? 10 + playerCount * 4 : 8 + playerCount * 3;
        int count = getRankIII() ? base + 9 : getRankII() ? base + 5 : base;
        if (ticksExisted > 200 && ticksExisted % (getRankIII() ? 170 : getRankII() ? 210 : 250) == 0)
            for (int i = 0; i < count; i++) {
                int x = source.getX() - 10 + rand.nextInt(20);
                int z = source.getZ() - 10 + rand.nextInt(20);
                int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, -1, z)).getY();

                EntitySkullLandmine landmine = new EntitySkullLandmine(world);
                if (i % 6 == 0)
                    landmine.setType(2);
                if (i % 5 == 0)
                    landmine.setType(1);
                landmine.setPosition(x + 0.5, y, z + 0.5);
                landmine.summoner = this;
                world.spawnEntity(landmine);
            }

        if (ConfigHandler.GAIA_DISARM)
            for (EntityPlayer player : getPlayersAround())
                if (!player.isCreative())
                    disarm(player);

        if (cd > 0 && getRankII())
            cd--;

        if (cd == 250 && skillType == 1)
            for (EntityPlayer player : getPlayersAround())
                if (world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaPreparing", I18n.format("entity.extrabotany:gaiaIII.name")).setStyle(new Style().setColor(TextFormatting.WHITE)));

        if (cd == 100 && skillType == 1)
            for (EntityPlayer player : getPlayersAround())
                if (world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning", I18n.format("entity.extrabotany:gaiaIII.name")).setStyle(new Style().setColor(TextFormatting.RED)));

        if (cd == 100 && skillType == 0)
            for (EntityPlayer player : getPlayersAround())
                if (world.isRemote)
                    player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning2", I18n.format("entity.extrabotany:gaiaIII.name")).setStyle(new Style().setColor(TextFormatting.RED)));

        if (cd == 0 && world.isRemote && skillType == 0 && !getPlayersAround().isEmpty()) {
            EntityPlayer player = getPlayersAround().get(world.rand.nextInt(getPlayersAround().size()));
            float amplifier = StatHandler.hasStat(player, LibAdvancements.GAIA_DEFEAT) ? 1.0F : 0.7F;
            player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning3", I18n.format("entity.extrabotany:gaiaIII.name")).setStyle(new Style().setColor(TextFormatting.RED)));
            ExtraBotanyAPI.dealTrueDamage(player, (player.getMaxHealth() * 0.20F + 6) * amplifier);
            cd = 350;
            skillType = getRankIII() ? 1 : world.rand.nextInt(2);
        }

        if (cd == 0 && !world.isRemote && skillType == 2) {
            if (ConfigHandler.GAIA_DIVINEJUDGE)
                spawnDivineJudge();
            else spawnMinion();
            cd = 300;
            skillType = getRankIII() ? 3 : 0;
        }

        if (cd == 0 && !world.isRemote && skillType == 3) {
            spawnMinion();
            cd = 340;
            skillType = world.rand.nextInt(1);
        }

        if (tpDelay > 0)
            tpDelay--;

        if (tpDelay == 0 && getHealth() > 0) {
            int tries = 0;
            while (!teleportRandomly() && tries < 50)
                tries++;
            if (tries >= 50)
                teleportTo(source.getX() + 0.5, source.getY() + 1.6, source.getZ() + 0.5);
            tpDelay = getRankIII() ? 65 : 75;
        }

        if (ticksExisted > 2600)
            for (EntityPlayer p : getPlayersAround())
                ExtraBotanyAPI.unlockAdvancement(p, LibAdvancements.MUSIC_ALL);

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

    private ItemStack parseItems(String str) {
        String[] entry = str.replace(" ", "").split(":");
        int meta = entry.length > 2 ? Integer.valueOf(entry[2]) : 0;
        ItemStack stack = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(entry[0], entry[1])), 1, meta);
        return stack;
    }

    private boolean match(ItemStack stack) {
        String m = stack.getItem().getRegistryName().toString();
        String[] whitelist = ConfigHandler.WHITELIST;
        if (whitelist.length > 0)
            for (int i = 0; i < whitelist.length; i++) {
                ItemStack compared = parseItems(whitelist[i]);
                compared.setCount(stack.getCount());
                if (stack.areItemStacksEqual(stack, compared))
                    return true;
            }
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

    private void spawnMinion() {
        for (int c = 0; c < 4; c++) {
            EntitySkullMinion m = new EntitySkullMinion(world);
            BlockPos p = getSource().add(MINION_LOCATIONS[c]);
            m.setPosition(p.getX(), p.getY(), p.getZ());
            m.setType(c);
            m.setShield(2);
            if (!world.isRemote)
                world.spawnEntity(m);
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
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeEntityToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setInteger(TAG_INVUL_TIME, getInvulTime());
        par1nbtTagCompound.setBoolean(TAG_AGGRO, aggro);

        par1nbtTagCompound.setInteger(TAG_SOURCE_X, source.getX());
        par1nbtTagCompound.setInteger(TAG_SOURCE_Y, source.getY());
        par1nbtTagCompound.setInteger(TAG_SOURCE_Z, source.getZ());

        par1nbtTagCompound.setInteger(TAG_PLAYER_COUNT, playerCount);
        par1nbtTagCompound.setInteger(TAG_SHIELD, getShield());
        par1nbtTagCompound.setBoolean(TAG_RANKII, getRankII());
        par1nbtTagCompound.setBoolean(TAG_RANKIII, getRankIII());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readEntityFromNBT(par1nbtTagCompound);
        setInvulTime(par1nbtTagCompound.getInteger(TAG_INVUL_TIME));
        aggro = par1nbtTagCompound.getBoolean(TAG_AGGRO);

        int x = par1nbtTagCompound.getInteger(TAG_SOURCE_X);
        int y = par1nbtTagCompound.getInteger(TAG_SOURCE_Y);
        int z = par1nbtTagCompound.getInteger(TAG_SOURCE_Z);
        source = new BlockPos(x, y, z);

        if (par1nbtTagCompound.hasKey(TAG_PLAYER_COUNT))
            playerCount = par1nbtTagCompound.getInteger(TAG_PLAYER_COUNT);
        else playerCount = 1;

        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomNameTag(@Nonnull String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void heal(float amount) {
        if (getInvulTime() == 0) {
            super.heal(amount);
        }
    }

    @Override
    public void onKillCommand() {
        this.setHealth(0.0F);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float par2) {
        Entity e = source.getTrueSource();

        if (e instanceof EntityPlayer && isTruePlayer(e)) {
            EntityPlayer player = (EntityPlayer) e;

            if (!playersWhoAttacked.contains(player.getUniqueID()))
                playersWhoAttacked.add(player.getUniqueID());

            int cap = 20;

            if (getRankII())
                teleportRandomly();

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
            ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.GAIA_DEFEAT);
            PlayerStatHandler.setGaiaDefeat(player, PlayerStatHandler.getGaiaDefeat(player) + 1);
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

    private List<EntityPlayer> getPlayersAround() {
        BlockPos source = getSource();
        float range = 15F;
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    private void particles() {
        BlockPos source = getSource();

        for (int i = 0; i < 360; i += 8) {
            float r = 0.3F;
            float g = 0F;
            float b = 1F;
            float m = 0.15F;
            float mv = 0.35F;

            float rad = i * (float) Math.PI / 180F;
            double x = source.getX() + 0.5 - Math.cos(rad) * ARENA_RANGE;
            double y = source.getY() + 0.5;
            double z = source.getZ() + 0.5 - Math.sin(rad) * ARENA_RANGE;

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

    // [VanillaCopy] EntityEnderman.teleportRandomly, edits noted.
    private boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double) (this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }

    // [VanillaCopy] EntityEnderman.teleportTo, edits noted.
    private boolean teleportTo(double x, double y, double z) {
		/* Botania - no events
		net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
		boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
		*/

        boolean flag = this.attemptTeleport(x, y, z);

        if (flag) {
            this.world.playSound((EntityPlayer) null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);

        }

        return flag;
    }

    // [VanillaCopy] of super, edits noted
    @Override
    public boolean attemptTeleport(double x, double y, double z) {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(this);
        World world = this.world;
        Random random = this.getRNG();

        if (world.isBlockLoaded(blockpos)) {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0) {
                BlockPos blockpos1 = blockpos.down();
                IBlockState iblockstate = world.getBlockState(blockpos1);

                if (iblockstate.getMaterial().blocksMovement()) {
                    flag1 = true;
                } else {
                    --this.posY;
                    blockpos = blockpos1;
                }
            }

            if (flag1) {
                this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

                if (world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(this.getEntityBoundingBox())) {
                    flag = true;
                }

                // Botania - Prevent out of bounds teleporting
                BlockPos source = getSource();
                if (vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(posX, posY, posZ, source.getX(), source.getY(), source.getZ()) > 12)
                    flag = false;
            }
        }

        if (!flag) {
            this.setPositionAndUpdate(d0, d1, d2);
            return false;
        } else {
            int i = 128;

            for (int j = 0; j < 128; ++j) {
                double d6 = (double) j / 127.0D;
                float f = (random.nextFloat() - 0.5F) * 0.2F;
                float f1 = (random.nextFloat() - 0.5F) * 0.2F;
                float f2 = (random.nextFloat() - 0.5F) * 0.2F;
                double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * (double) this.width * 2.0D;
                double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * (double) this.height;
                double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * (double) this.width * 2.0D;
                world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double) f, (double) f1, (double) f2, new int[0]);
            }

            // Botania - invalid/unneeded check
			/*if (this instanceof EntityCreature)
			{
				((EntityCreature)this).getNavigator().clearPathEntity();
			}*/

            // Botania - damage any players in our way
            Vec3d origPos = new Vec3d(d0, d1 + height / 2, d2);
            Vec3d newPos = new Vec3d(posX, posY + height / 2, posZ);

            if (origPos.squareDistanceTo(newPos) > 1) {
                for (EntityPlayer player : getPlayersAround()) {
                    RayTraceResult rtr = player.getEntityBoundingBox().grow(0.25).calculateIntercept(origPos, newPos);
                    if (rtr != null)
                        player.attackEntityFrom(DamageSource.causeMobDamage(this), 6);
                }
            }

            return true;
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
        private final EntityGaiaIII guardian;

        public DopplegangerMusic(EntityGaiaIII guardian) {
            super(com.meteor.extrabotany.common.core.handler.ModSounds.gaiaMusic3, SoundCategory.RECORDS);
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
