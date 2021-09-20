package com.meteor.extrabotany.common.entities.ego;

import com.google.common.collect.ImmutableList;
import com.meteor.extrabotany.client.renderer.entity.layers.HeldFakeItemLayer;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.ContributorListHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.relic.ItemInfluxWaver;
import com.meteor.extrabotany.common.items.relic.ItemStarWrath;
import com.meteor.extrabotany.common.items.relic.ItemTrueShadowKatana;
import com.meteor.extrabotany.common.items.relic.ItemTrueTerrablade;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.common.core.helper.Vector3;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntityEGOMinion extends MonsterEntity {

    public EntityEGO summoner;

    private static final List<BlockPos> SPAWN_LOCATIONS = ImmutableList.of(
            new BlockPos(6, 1, 6),
            new BlockPos(6, 1, -6),
            new BlockPos(-6, 1, 6),
            new BlockPos(-6, 1, -6)
    );

    private static final String TAG_TYPE = "type";

    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityEGOMinion.class,
            DataSerializers.VARINT);

    private int attackCooldown = 0;
    private int tp = 0;

    public EntityEGOMinion(EntityType<? extends MonsterEntity> p_i48576_1_, World p_i48576_2_) {
        super(p_i48576_1_, p_i48576_2_);
    }

    public EntityEGOMinion(World p_i48576_2_) {
        super(ModEntities.EGOMINION, p_i48576_2_);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {

    }

    @Override
    public void livingTick() {
        super.livingTick();

        if(!world.isRemote) {
            clearPotions(this);
            if (world.getDifficulty() == Difficulty.PEACEFUL || summoner == null || summoner.removed) {
                remove();
            }
        }

        float RANGE = 16F;
        AxisAlignedBB axis = new AxisAlignedBB(getPositionVec().add(-RANGE, -RANGE, -RANGE)
                , getPositionVec().add(RANGE + 1, RANGE + 1, RANGE + 1));
        if(getAttackTarget() == null || !(getAttackTarget() instanceof PlayerEntity)){
            List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, axis);
            if(players.size() > 0) {
                setAttackTarget(players.get(0));
            }
        }

        if(getAttackTarget() != null){
            Vector3d lookVec = getAttackTarget().getLookVec().mul(1, 0, 1);

            double playerRot = Math.toRadians(getAttackTarget().rotationYaw + 90);
            if (lookVec.x == 0 && lookVec.z == 0) {
                lookVec = new Vector3d(Math.cos(playerRot), 0, Math.sin(playerRot));
            }

            lookVec = lookVec.normalize().mul(3.5F, 0, 3.5F);

            if(this.getHealth() <= this.getMaxHealth() * 0.5F){
                lookVec = lookVec.mul(-2F, 0, -2F);
                if(ticksExisted % 40 == 0)
                    this.heal(2F);
            }

            lookVec = lookVec.rotateYaw((float) (Math.PI / 2F * getMinionType() + Math.floor(ticksExisted / 100) * Math.PI / 4F));

            Vector3d targetPos = getAttackTarget().getPositionVec().add(lookVec);

            if(!world.isRemote) {
                if (this.getPositionVec().distanceTo(targetPos) >= 0.5F) {
                    this.getMoveHelper().setMoveTo(targetPos.x, targetPos.y, targetPos.z, 0.7F);
                    tp++;
                } else {
                    tp = 0;
                }

                if(tp >= 60){
                    this.setPositionAndUpdate(targetPos.x, targetPos.y, targetPos.z);
                    tp = 0;
                }
            }
        }

        if(this.attackCooldown > 0)
            this.attackCooldown--;

        if(this.attackCooldown == 0) {
            if(tryAttack())
                this.attackCooldown = 90 + world.rand.nextInt(40);
        }

    }

    public boolean tryAttack(){
        if(getAttackTarget() == null)
            return false;

        this.swingArm(Hand.MAIN_HAND);
        if(!world.isRemote) {
            switch (getMinionType()) {
                case 0: {
                    ((ItemTrueShadowKatana) ModItems.trueshadowkatana).attackEntity(this, getAttackTarget());
                    break;
                }
                case 1: {
                    ((ItemTrueTerrablade) ModItems.trueterrablade).attackEntity(this, getAttackTarget());
                    break;
                }
                case 2: {
                    ((ItemInfluxWaver) ModItems.influxwaver).attackEntity(this, getAttackTarget());
                    break;
                }
                case 3: {
                    ((ItemStarWrath) ModItems.starwrath).attackEntity(this, getAttackTarget());
                    break;
                }
            }
        }
        return true;
    }

    public void clearPotions(LivingEntity player) {
        List<Effect> potionsToRemove = player.getActivePotionEffects().stream()
                .filter(effect -> effect.getPotion().getEffectType() == EffectType.HARMFUL)
                .map(EffectInstance::getPotion)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            player.removePotionEffect(potion);
            ((ServerWorld) player.world).getChunkProvider().sendToTrackingAndSelf(player,
                    new SRemoveEntityEffectPacket(player.getEntityId(), potion));
        });
    }

    @Override
    public void tick(){
        super.tick();

    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        Entity e = source.getTrueSource();
        if (e instanceof PlayerEntity && isTruePlayer(e)) {
            float RANGE = 8F;
            AxisAlignedBB axis = new AxisAlignedBB(getPositionVec().add(-RANGE, -RANGE, -RANGE)
                    , getPositionVec().add(RANGE + 1, RANGE + 1, RANGE + 1));
            List<EntityEGOMinion> minions = world.getEntitiesWithinAABB(EntityEGOMinion.class, axis);
            float resistance = Math.min(0.6F, minions.size() * 0.15F);
            int cap = 20;
            return super.attackEntityFrom(source, Math.min(cap, amount * (1F - resistance)));
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
    public void registerData() {
        super.registerData();
        dataManager.register(TYPE, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        cmp.putInt(TAG_TYPE, getMinionType());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
        setMinionType(cmp.getInt(TAG_TYPE));
    }

    public static void spawn(EntityEGO summoner, World world, BlockPos pos, float health){
        List<String> names = new ArrayList<>(ContributorListHandler.contributorsMap.keySet());
        Collections.shuffle(names);
        if(names.isEmpty()){
            names.add("ExtraMeteorP");
            names.add("Vazkii");
            names.add("Notch");
            names.add("LexManos");
        }
        if (!world.isRemote) {
            int type = 0;
            for (BlockPos spawnpos : SPAWN_LOCATIONS) {
                EntityEGOMinion minion = new EntityEGOMinion(world);
                minion.summoner = summoner;
                BlockPos mpos = pos.add(spawnpos.getX(), spawnpos.getY(), spawnpos.getZ());
                minion.setPosition(mpos.getX(), mpos.getY(), mpos.getZ());
                minion.setCustomName(new StringTextComponent(names.get(type)));
                minion.setMinionType(type++);
                minion.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
                minion.getAttribute(Attributes.ARMOR).setBaseValue(10);
                minion.onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(minion.getPosition()), SpawnReason.EVENT, null, null);
                world.addEntity(minion);
            }
        }
    }

    public ItemStack getWeapon(){
        switch (getMinionType()){
            case 0:
                return new ItemStack(ModItems.trueshadowkatana);
            case 1:
                return new ItemStack(ModItems.trueterrablade);
            case 2:
                return new ItemStack(ModItems.influxwaver);
            case 3:
                return new ItemStack(ModItems.starwrath);
            default:
                return new ItemStack(ModItems.excaliber);
        }
    }

    public int getMinionType() {
        return dataManager.get(TYPE);
    }

    public void setMinionType(int i) {
        dataManager.set(TYPE, i);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
