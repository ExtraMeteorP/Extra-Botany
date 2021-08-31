package com.meteor.extrabotany.common.entities.ego;

import com.google.common.collect.ImmutableList;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.relic.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.client.core.handler.BossBarHandler;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.lib.ModTags;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntityEGO extends MobEntity implements IEntityAdditionalSpawnData {

    public static final float ARENA_RANGE = 15F;
    public static final int ARENA_HEIGHT = 5;

    public static final float MAX_HP = 600F;

    private static final String TAG_INVUL_TIME = "invulTime";
    private static final String TAG_SOURCE_X = "sourceX";
    private static final String TAG_SOURCE_Y = "sourceY";
    private static final String TAG_SOURCE_Z = "sourcesZ";
    private static final String TAG_PLAYER_COUNT = "playerCount";
    private static final String TAG_STAGE = "stage";
    private static final String TAG_WEAPONTYPE = "weapontype";
    private static final ITag.INamedTag<Block> BLACKLIST = ModTags.Blocks.GAIA_BREAK_BLACKLIST;

    private static final DataParameter<Integer> INVUL_TIME = EntityDataManager.createKey(EntityEGO.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(EntityEGO.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> WEAPON_TYPE = EntityDataManager.createKey(EntityEGO.class, DataSerializers.VARINT);

    private static final List<BlockPos> PYLON_LOCATIONS = ImmutableList.of(
            new BlockPos(4, 1, 4),
            new BlockPos(4, 1, -4),
            new BlockPos(-4, 1, 4),
            new BlockPos(-4, 1, -4)
    );

    private static final List<ResourceLocation> CHEATY_BLOCKS = Arrays.asList(
            new ResourceLocation("openblocks", "beartrap"),
            new ResourceLocation("thaumictinkerer", "magnet")
    );

    private int changeWeaponDelay = 0;
    private int attackDelay = 0;
    private float damageTaken = 0;
    private int tpDelay = 0;
    private int playerCount = 0;
    private BlockPos source = BlockPos.ZERO;
    private final List<UUID> playersWhoAttacked = new ArrayList<>();
    private final ServerBossInfo bossInfo = (ServerBossInfo) new ServerBossInfo(ModEntities.EGO.getName(), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS).setCreateFog(true);;
    private UUID bossInfoUUID = bossInfo.getUniqueId();
    public PlayerEntity trueKiller = null;
    private int MAX_WAVE = 5;
    private int wave = 0;
    private Integer[] waves = new Integer[]{0, 1, 2, 3, 4};

    public EntityEGO(EntityType<EntityEGO> type, World world) {
        super(type, world);
        experienceValue = 825;
    }

    public static boolean spawn(PlayerEntity player, ItemStack stack, World world, BlockPos pos) {
        //initial checks
        if (!(world.getTileEntity(pos) instanceof BeaconTileEntity) ||
                !isTruePlayer(player) ||
                countEGOAround(world, pos) > 0) {
            return false;
        }

        //check difficulty
        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            if (!world.isRemote) {
                player.sendMessage(new TranslationTextComponent("botaniamisc.peacefulNoob").mergeStyle(TextFormatting.RED), Util.DUMMY_UUID);
            }
            return false;
        }

        //check pylons
        List<BlockPos> invalidPylonBlocks = checkPylons(world, pos);
        if (!invalidPylonBlocks.isEmpty()) {
            if (world.isRemote) {
                warnInvalidBlocks(world, invalidPylonBlocks);
            } else {
                player.sendMessage(new TranslationTextComponent("botaniamisc.needsCatalysts").mergeStyle(TextFormatting.RED), Util.DUMMY_UUID);
            }

            return false;
        }

        //check arena shape
        List<BlockPos> invalidArenaBlocks = checkArena(world, pos);
        if (!invalidArenaBlocks.isEmpty()) {
            if (world.isRemote) {
                warnInvalidBlocks(world, invalidArenaBlocks);
            } else {
                PacketHandler.sendTo((ServerPlayerEntity) player,
                        new PacketBotaniaEffect(PacketBotaniaEffect.EffectType.ARENA_INDICATOR, pos.getX(), pos.getY(), pos.getZ()));

                player.sendMessage(new TranslationTextComponent("botaniamisc.badArena").mergeStyle(TextFormatting.RED), Util.DUMMY_UUID);
            }

            return false;
        }

        //all checks ok, spawn the boss
        if (!world.isRemote) {
            stack.shrink(1);

            EntityEGO e = ModEntities.EGO.create(world);
            e.setPosition(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
            e.source = pos;
            e.setWeaponType(0);
            e.setCustomName(player.getDisplayName());

            int playerCount = e.getPlayersAround().size();
            e.playerCount = playerCount;
            e.setInvulTime(0);
            e.getAttribute(Attributes.ARMOR).setBaseValue(20);
            e.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MAX_HP * playerCount);
            e.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 10F, 0.1F);
            e.onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(e.getPosition()), SpawnReason.EVENT, null, null);
            world.addEntity(e);
        }

        return true;
    }

    private static List<BlockPos> checkPylons(World world, BlockPos beaconPos) {
        List<BlockPos> invalidPylonBlocks = new ArrayList<>();

        for (BlockPos coords : PYLON_LOCATIONS) {
            BlockPos pos_ = beaconPos.add(coords);

            BlockState state = world.getBlockState(pos_);
            if (state.getBlock() != ModBlocks.gaiaPylon) {
                invalidPylonBlocks.add(pos_);
            }
        }

        return invalidPylonBlocks;
    }

    private static List<BlockPos> checkArena(World world, BlockPos beaconPos) {
        List<BlockPos> trippedPositions = new ArrayList<>();
        int range = (int) Math.ceil(ARENA_RANGE);
        BlockPos pos;

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                if (Math.abs(x) == 4 && Math.abs(z) == 4 || vazkii.botania.common.core.helper.MathHelper.pointDistancePlane(x, z, 0, 0) > ARENA_RANGE) {
                    continue; // Ignore pylons and out of circle
                }

                boolean hasFloor = false;

                for (int y = -2; y <= ARENA_HEIGHT; y++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue; //the beacon
                    }

                    pos = beaconPos.add(x, y, z);

                    BlockState state = world.getBlockState(pos);

                    boolean allowBlockHere = y < 0;
                    boolean isBlockHere = !state.getCollisionShape(world, pos).isEmpty();

                    if (allowBlockHere && isBlockHere) //floor is here! good
                    {
                        hasFloor = true;
                    }

                    if (y == 0 && !hasFloor) //column is entirely missing floor
                    {
                        trippedPositions.add(pos.down());
                    }

                    if (!allowBlockHere && isBlockHere && !BLACKLIST.contains(state.getBlock())) //ceiling is obstructed in this column
                    {
                        trippedPositions.add(pos);
                    }
                }
            }
        }

        return trippedPositions;
    }

    private static void warnInvalidBlocks(World world, Iterable<BlockPos> invalidPositions) {
        WispParticleData data = WispParticleData.wisp(0.5F, 1, 0.2F, 0.2F, 8, false);
        for (BlockPos pos_ : invalidPositions) {
            world.addParticle(data, pos_.getX() + 0.5, pos_.getY() + 0.5, pos_.getZ() + 0.5, 0, 0, 0);
        }
    }

    public ItemStack getWeapon(){
        switch (getWeaponType()){
            case 0:
                return new ItemStack(ModItems.trueshadowkatana);
            case 1:
                return new ItemStack(ModItems.trueterrablade);
            case 2:
                return new ItemStack(ModItems.influxwaver);
            case 3:
                return new ItemStack(ModItems.starwrath);
            case 4:
                return new ItemStack(ModItems.firstfractal);
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, ARENA_RANGE * 1.5F));
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(INVUL_TIME, 0);
        dataManager.register(STAGE, 0);
        dataManager.register(WEAPON_TYPE, 0);
    }

    public int getInvulTime() {
        return dataManager.get(INVUL_TIME);
    }

    public BlockPos getSource() {
        return source;
    }

    public void setInvulTime(int time) {
        dataManager.set(INVUL_TIME, time);
    }

    public int getStage() {
        return dataManager.get(STAGE);
    }

    public void setStage(int time) {
        dataManager.set(STAGE, time);
    }

    public int getWeaponType() {
        return dataManager.get(WEAPON_TYPE);
    }

    public void setWeaponType(int time) {
        dataManager.set(WEAPON_TYPE, time);
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        cmp.putInt(TAG_INVUL_TIME, getInvulTime());

        cmp.putInt(TAG_SOURCE_X, source.getX());
        cmp.putInt(TAG_SOURCE_Y, source.getY());
        cmp.putInt(TAG_SOURCE_Z, source.getZ());

        cmp.putInt(TAG_PLAYER_COUNT, playerCount);
        cmp.putInt(TAG_STAGE, getStage());
        cmp.putInt(TAG_WEAPONTYPE, getWeaponType());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
        setInvulTime(cmp.getInt(TAG_INVUL_TIME));

        int x = cmp.getInt(TAG_SOURCE_X);
        int y = cmp.getInt(TAG_SOURCE_Y);
        int z = cmp.getInt(TAG_SOURCE_Z);
        source = new BlockPos(x, y, z);

        if (cmp.contains(TAG_PLAYER_COUNT)) {
            playerCount = cmp.getInt(TAG_PLAYER_COUNT);
        } else {
            playerCount = 1;
        }

        setStage(cmp.getInt(TAG_STAGE));
        setWeaponType(cmp.getInt(TAG_WEAPONTYPE));

        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        Entity e = source.getTrueSource();
        if (e instanceof PlayerEntity && isTruePlayer(e) && getInvulTime() == 0) {
            PlayerEntity player = (PlayerEntity) e;

            if (!playersWhoAttacked.contains(player.getUniqueID())) {
                playersWhoAttacked.add(player.getUniqueID());
            }

            int cap = 25;
            float dmg = Math.min(cap, amount);
            damageTaken+=dmg;

            if(damageTaken >= 50){
                if(tryAttack()) {
                    damageTaken = 0;
                    teleportRandomly();
                }
            }
            return super.attackEntityFrom(source, dmg);
        }

        return false;
    }

    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    public static boolean isTruePlayer(Entity e) {
        if (!(e instanceof PlayerEntity)) {
            return false;
        }

        PlayerEntity player = (PlayerEntity) e;

        String name = player.getName().getString();
        return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
    }

    @Override
    public void onDeath(@Nonnull DamageSource source) {
        super.onDeath(source);
        LivingEntity entitylivingbase = getAttackingEntity();

        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 20F, (1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        world.addParticle(ParticleTypes.EXPLOSION_EMITTER, getPosX(), getPosY(), getPosZ(), 1D, 0D, 0D);
    }

    @Override
    public boolean canDespawn(double dist) {
        return false;
    }

    @Override
    protected void dropLoot(@Nonnull DamageSource source, boolean wasRecentlyHit) {
        // Save true killer, they get extra loot
        if (wasRecentlyHit && source.getTrueSource() instanceof PlayerEntity) {
            trueKiller = (PlayerEntity) source.getTrueSource();
        }

        // Generate loot table for every single attacking player
        for (UUID u : playersWhoAttacked) {
            PlayerEntity player = world.getPlayerByUuid(u);
            if (player == null) {
                continue;
            }

            PlayerEntity saveLastAttacker = attackingPlayer;
            Vector3d savePos = getPositionVec();

            attackingPlayer = player; // Fake attacking player as the killer
            // Spoof pos so drops spawn at the player
            setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            super.dropLoot(DamageSource.causePlayerDamage(player), wasRecentlyHit);
            setPosition(savePos.getX(), savePos.getY(), savePos.getZ());
            attackingPlayer = saveLastAttacker;
        }

        trueKiller = null;
    }

    public List<PlayerEntity> getPlayersAround() {
        float range = 15F;
        return world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range), player -> isTruePlayer(player) && !player.isSpectator());
    }

    private static int countEGOAround(World world, BlockPos source) {
        float range = 15F;
        List<EntityEGO> l = world.getEntitiesWithinAABB(EntityEGO.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
        return l.size();
    }

    private static int countEGOMinionAround(World world, BlockPos source) {
        float range = 15F;
        List<EntityEGOMinion> l = world.getEntitiesWithinAABB(EntityEGOMinion.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
        return l.size();
    }

    private void smashBlocksAround(int centerX, int centerY, int centerZ, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius + 1; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int x = centerX + dx;
                    int y = centerY + dy;
                    int z = centerZ + dz;

                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();

                    if (state.getBlockHardness(world, pos) == -1) {
                        continue;
                    }

                    if (CHEATY_BLOCKS.contains(Registry.BLOCK.getKey(block))) {
                        world.destroyBlock(pos, true);
                    } else {
                        //don't break blacklisted blocks
                        if (BLACKLIST.contains(block)) {
                            continue;
                        }
                        //don't break the floor
                        if (y < source.getY()) {
                            continue;
                        }
                        //don't break blocks in pylon columns
                        if (Math.abs(source.getX() - x) == 4 && Math.abs(source.getZ() - z) == 4) {
                            continue;
                        }

                        world.destroyBlock(pos, true);
                    }
                }
            }
        }
    }

    private void clearPotions(PlayerEntity player) {
        List<Effect> potionsToRemove = player.getActivePotionEffects().stream()
                .filter(effect -> effect.getDuration() < 160 && effect.isAmbient() && effect.getPotion().getEffectType() != EffectType.HARMFUL)
                .map(EffectInstance::getPotion)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            player.removePotionEffect(potion);
            ((ServerWorld) world).getChunkProvider().sendToTrackingAndSelf(player,
                    new SRemoveEntityEffectPacket(player.getEntityId(), potion));
        });
    }

    private void keepInsideArena(PlayerEntity player) {
        if (vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(player.getPosX(), player.getPosY(), player.getPosZ(), source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5) >= ARENA_RANGE) {
            Vector3 sourceVector = new Vector3(source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5);
            Vector3 playerVector = Vector3.fromEntityCenter(player);
            Vector3 motion = sourceVector.subtract(playerVector).normalize();

            player.setMotion(motion.x, 0.2, motion.z);
            player.velocityChanged = true;
        }
    }

    private void particles() {
        for (int i = 0; i < 360; i += 8) {
            float r = 0.6F;
            float g = 0F;
            float b = 0.2F;
            float m = 0.15F;
            float mv = 0.35F;

            float rad = i * (float) Math.PI / 180F;
            double x = source.getX() + 0.5 - Math.cos(rad) * ARENA_RANGE;
            double y = source.getY() + 0.5;
            double z = source.getZ() + 0.5 - Math.sin(rad) * ARENA_RANGE;

            WispParticleData data = WispParticleData.wisp(0.5F, r, g, b);
            world.addParticle(data, x, y, z, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * mv, (float) (Math.random() - 0.5F) * m);
        }

        if (getInvulTime() >= 20) {
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

                float r = 0.7F + (float) Math.random() * 0.3F;
                float g = (float) Math.random() * 0.3F;
                float b = 0.7F + (float) Math.random() * 0.3F;

                WispParticleData data = WispParticleData.wisp(0.25F + (float) Math.random() * 0.1F, r, g, b, 1);
                world.addParticle(data, partPos.x, partPos.y, partPos.z, 0, -(-0.075F - (float) Math.random() * 0.015F), 0);
                WispParticleData data1 = WispParticleData.wisp(0.4F, r, g, b);
                world.addParticle(data1, partPos.x, partPos.y, partPos.z, (float) mot.x, (float) mot.y, (float) mot.z);
            }
        }
    }

    public boolean tryAttack(){
        if(getPlayersAround().isEmpty())
            return false;

        Entity target = getPlayersAround().get(0);

        this.swingArm(Hand.MAIN_HAND);
        if(!world.isRemote) {
            switch (getWeaponType()) {
                case 0: {
                    ((ItemTrueShadowKatana) ModItems.trueshadowkatana).attackEntity(this, target);
                    break;
                }
                case 1: {
                    ((ItemTrueTerrablade) ModItems.trueterrablade).attackEntity(this, target);
                    break;
                }
                case 2: {
                    ((ItemInfluxWaver) ModItems.influxwaver).attackEntity(this, target);
                    break;
                }
                case 3: {
                    ((ItemStarWrath) ModItems.starwrath).attackEntity(this, target);
                    break;
                }
                case 4: {
                    ((ItemFirstFractal) ModItems.firstfractal).attackEntity(this, target);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        int invul = getInvulTime();

        List<Integer> WAVES = Arrays.asList(waves);

        if(attackDelay > 0){
            attackDelay--;
        }else{
            if(tryAttack()){
                int delay = (int) (80 - getStage() * 15 + 15 * Math.random());
                attackDelay = delay;
            }
        }

        if (world.isRemote) {
            particles();
            PlayerEntity player = Botania.proxy.getClientPlayer();
            if (getPlayersAround().contains(player)) {
                player.abilities.isFlying &= player.abilities.isCreativeMode;
            }
            return;
        }

        bossInfo.setPercent(getHealth() / getMaxHealth());

        if (isPassenger()) {
            stopRiding();
        }

        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            remove();
        }

        if(invul > 0){
            setInvulTime(invul - 1);
            if(getStage() == 1){
                if(invul >= 20){
                    setMotion(getMotion().getX(), 0, getMotion().getZ());
                    if(invul % 60 == 0)
                        if(wave < MAX_WAVE){
                            EntityEGOLandmine.spawnLandmine(wave, world, source, this);
                            wave++;
                        }
                    return;
                }

            }
            if(getStage() == 2){
                if(invul >= 20){
                    setMotion(getMotion().getX(), 0, getMotion().getZ());
                    setHealth(getHealth() + 0.25F);
                    if(countEGOMinionAround(world, source) == 0)
                        setInvulTime(0);
                    return;
                }
            }
        }

        smashBlocksAround(MathHelper.floor(getPosX()), MathHelper.floor(getPosY()), MathHelper.floor(getPosZ()), 1);

        List<PlayerEntity> players = getPlayersAround();

        if (players.isEmpty() && !world.getPlayers().isEmpty()) {
            remove();
        } else {
            for (PlayerEntity player : players) {
                //also see SleepingHandler
                if (player.isSleeping()) {
                    player.wakeUp();
                }

                clearPotions(player);
                keepInsideArena(player);
                player.abilities.isFlying &= player.abilities.isCreativeMode;
            }
        }

        if (!isAlive() || players.isEmpty()) {
            return;
        }

        if(changeWeaponDelay > 0){
            changeWeaponDelay--;
        }else{
            changeWeaponDelay = 160;
            int weaponType = getStage() == 0 ? world.rand.nextInt(2) : getStage() == 1 ? world.rand.nextInt(4) : 4;
            setWeaponType(weaponType);
        }

        if(tpDelay > 0){
            tpDelay--;
        }else{
            if(tryAttack()) {
                teleportRandomly();
                tpDelay = 100 - getStage() * 10;
            }
        }

        if(getStage() == 0 && getHealth() < 0.75F * getMaxHealth()) {
            setStage(1);
            setInvulTime(300);
            Collections.shuffle(WAVES);
            this.setPositionAndUpdate(source.getX()+0.5, source.getY()+3, source.getZ()+0.5);
        }
        if(getStage() == 1 && getHealth() < 0.25F * getMaxHealth()){
            setStage(2);
            setInvulTime(600);
            setWeaponType(4);
            EntityEGOMinion.spawn(world, getSource(), 60F * playerCount);
            this.setPositionAndUpdate(source.getX()+0.5, source.getY()+3, source.getZ()+0.5);
        }

    }

    @Override
    public void setHealth(float f){
        super.setHealth(f);
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    @Override
    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    protected void collideWithNearbyEntities() {
        if (getInvulTime() == 0) {
            super.collideWithNearbyEntities();
        }
    }

    @Override
    public boolean canBePushed() {
        return super.canBePushed() && getInvulTime() == 0;
    }

    private void teleportRandomly() {
        //choose a location to teleport to
        double oldX = getPosX(), oldY = getPosY(), oldZ = getPosZ();
        double newX, newY = source.getY(), newZ;
        int tries = 0;

        do {
            newX = source.getX() + (rand.nextDouble() - .5) * ARENA_RANGE;
            newZ = source.getZ() + (rand.nextDouble() - .5) * ARENA_RANGE;
            tries++;
            //ensure it's inside the arena ring, and not just its bounding square
        } while (tries < 50 && vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(newX, newY, newZ, source.getX(), source.getY(), source.getZ()) > 12);

        if (tries == 50) {
            //failsafe: teleport to the beacon
            newX = source.getX() + .5;
            newY = source.getY() + 1.6;
            newZ = source.getZ() + .5;
        }

        //for low-floor arenas, ensure landing on the ground
        BlockPos tentativeFloorPos = new BlockPos(newX, newY - 1, newZ);
        if (world.getBlockState(tentativeFloorPos).getCollisionShape(world, tentativeFloorPos).isEmpty()) {
            newY--;
        }

        //teleport there
        setPositionAndUpdate(newX, newY, newZ);

        //play sound
        world.playSound(null, oldX, oldY, oldZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
        this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);

        Random random = getRNG();

        //spawn particles along the path
        int particleCount = 128;
        for (int i = 0; i < particleCount; ++i) {
            double progress = i / (double) (particleCount - 1);
            float vx = (random.nextFloat() - 0.5F) * 0.2F;
            float vy = (random.nextFloat() - 0.5F) * 0.2F;
            float vz = (random.nextFloat() - 0.5F) * 0.2F;
            double px = oldX + (newX - oldX) * progress + (random.nextDouble() - 0.5D) * getWidth() * 2.0D;
            double py = oldY + (newY - oldY) * progress + random.nextDouble() * getHeight();
            double pz = oldZ + (newZ - oldZ) * progress + (random.nextDouble() - 0.5D) * getWidth() * 2.0D;
            world.addParticle(ParticleTypes.PORTAL, px, py, pz, vx, vy, vz);
        }

        Vector3d oldPosVec = new Vector3d(oldX, oldY + getHeight() / 2, oldZ);
        Vector3d newPosVec = new Vector3d(newX, newY + getHeight() / 2, newZ);

        if (oldPosVec.squareDistanceTo(newPosVec) > 1) {
            //damage players in the path of the teleport
            for (PlayerEntity player : getPlayersAround()) {
                boolean hit = player.getBoundingBox().grow(0.25).rayTrace(oldPosVec, newPosVec)
                        .isPresent();
                if (hit) {
                    player.attackEntityFrom(DamageSource.causeMobDamage(this), 6);
                }
            }

            //break blocks in the path of the teleport
            int breakSteps = (int) oldPosVec.distanceTo(newPosVec);
            if (breakSteps >= 2) {
                for (int i = 0; i < breakSteps; i++) {
                    float progress = i / (float) (breakSteps - 1);
                    int breakX = MathHelper.floor(oldX + (newX - oldX) * progress);
                    int breakY = MathHelper.floor(oldY + (newY - oldY) * progress);
                    int breakZ = MathHelper.floor(oldZ + (newZ - oldZ) * progress);

                    smashBlocksAround(breakX, breakY, breakZ, 1);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBossBarTexture() {
        return BossBarHandler.defaultBossBar;
    }

    @OnlyIn(Dist.CLIENT)
    public Rectangle2d getBossBarTextureRect() {
        return new Rectangle2d(0, 0, 185, 15);
    }

    @OnlyIn(Dist.CLIENT)
    public Rectangle2d getBossBarHPTextureRect() {
        Rectangle2d barRect = getBossBarTextureRect();
        return new Rectangle2d(0, barRect.getY() + barRect.getHeight(), 181, 7);
    }

    @OnlyIn(Dist.CLIENT)
    public int bossBarRenderCallback(MatrixStack ms, int x, int y) {
        ms.push();
        int px = x + 160;
        int py = y + 12;

        Minecraft mc = Minecraft.getInstance();
        ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
        mc.getItemRenderer().renderItemIntoGUI(stack, px, py);

        mc.fontRenderer.drawStringWithShadow(ms, Integer.toString(playerCount), px + 15, py + 4, 0xFFFFFF);
        ms.pop();

        return 5;
    }

    public UUID getBossInfoUuid() {
        return bossInfoUUID;
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(playerCount);
        buffer.writeLong(source.toLong());
        buffer.writeLong(bossInfoUUID.getMostSignificantBits());
        buffer.writeLong(bossInfoUUID.getLeastSignificantBits());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void readSpawnData(PacketBuffer additionalData) {
        playerCount = additionalData.readInt();
        source = BlockPos.fromLong(additionalData.readLong());
        long msb = additionalData.readLong();
        long lsb = additionalData.readLong();
        bossInfoUUID = new UUID(msb, lsb);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }
}
