package com.meteor.extrabotany.common.entities.mountable;

import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.entities.EntityKeyOfTruth;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityMotor extends EntityMountable {

    private static final String TAG_CYCLONETICKS = "cycloneticks";
    private static final String TAG_TECTONICENERGY = "tectonicenergy";

    private static final DataParameter<Integer> CYCLONE_TICKS = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> TECTONIC_ENERGY = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.VARINT);

    public int ridingTicks = 0;

    public EntityMotor(EntityType<? extends EntityMotor> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
    }

    public EntityMotor(World worldIn) {
        super(ModEntities.MOTOR, worldIn);
    }

    public EntityMotor(World worldIn, double x, double y, double z) {
        super(ModEntities.MOTOR, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(0, 0, 0);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) this.getPassengers().get(0);
            Entity passenger = getPassengers().size() > 1 ? getPassengers().get(1) : null;
            if(entityIn != player && entityIn != passenger) {
                HerrscherHandler.iceAttack(entityIn, player, 7F);
                if(!(entityIn instanceof PlayerEntity))
                    player.setLastAttackedEntity(entityIn);
                if (entityIn instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity) entityIn;

                    //living.knockBack(player, 0.5F, -living.getPosX() + player.getPosX(), -living.getPosZ() + player.getPosZ());
                }
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TECTONIC_ENERGY, 0);
        this.dataManager.register(CYCLONE_TICKS, 0);
    }

    @Override
    public double getMountedYOffset() {
        return 0.225D;
    }

    @Override
    public Item getItemBoat() {
        return ModItems.motor;
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = null;
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof PlayerEntity) {
            player = (PlayerEntity) this.getPassengers().get(0);

            if (this.getCycloneTicks() > 0) {
                setPitch(-5);
                if (this.getCycloneTicks() > 6)
                    setRotation(-5);
            }

            float speed = 1.65F;
            double mx = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * speed);
            double mz = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * speed);
            double my = this.getMotion().y;

            if(this.forwardInputDown){
                this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY(), this.getPosZ(), -mx, 0.15F, -mz);
            }

            if (this.rightInputDown) {
                setRotation(-5);
            } else if (this.leftInputDown) {
                setRotation(5);
            } else {
                setRotation(0);
                setPitch(0);
            }

            ridingTicks++;

            if (this.forwardInputDown && this.collidedHorizontally) {
                this.setMotion(this.getMotion().x, this.getMotion().y+0.08f, this.getMotion().z);
            }

            if (ridingTicks >= 120) {

                if(this.getTectonicEnergy() < 800)
                    this.setTectonicEnergy(Math.min(800, this.getTectonicEnergy() + 2));

                if (this.spaceInputDown){
                    if(this.getTectonicEnergy() >= 200) {
                        this.setTectonicEnergy(Math.max(0, this.getTectonicEnergy() - 6));
                        this.setMotion(mx, my, mz);
                        if (this.world.getBlockState(new BlockPos(getPosX(), getPosY(), getPosZ()).add(mx,my,mz)).getBlock() != Blocks.AIR)
                            this.setPosition(this.getPosX(), this.getPosY() + 1F, this.getPosZ());
                    }else
                        this.setTectonicEnergy(0);
                }

                for (LivingEntity living : getEntitiesAround()) {
                    if (living == player)
                        continue;
                    if(getPassengers().size() > 1 && living == getPassengers().get(1))
                        continue;
                    if (!living.canEntityBeSeen(player))
                        continue;
                    if ((living instanceof IMob || player.getLastAttackedEntity() == living) && ticksExisted % 15 == 0
                    ) {
                        EntityKeyOfTruth key = new EntityKeyOfTruth(world, player);
                        key.setPosition(player.getPosX() - Math.random() * 2F + 1F, player.getPosY() + 2.2F,
                                player.getPosZ() - Math.random() * 2F + 1F);
                        if (Math.random() < 0.5F)
                            key.setPosition(living.getPosX() - Math.random() * 2F + 1F, living.getPosY() + 2.2F,
                                    living.getPosZ() - Math.random() * 2F + 1F);
                        key.rotationYaw = player.rotationYaw;
                        key.setPitch(-player.rotationPitch);
                        key.setRotation(MathHelper.wrapDegrees(-player.rotationYaw + 180));
                        if (!world.isRemote) {
                            world.addEntity(key);
                        }
                        break;
                    }
                }

                if(this.getCycloneTicks() == 0 && this.ctrlInputDown && this.getTectonicEnergy() >= 400){
                    this.setCycloneTicks(15);
                    this.setTectonicEnergy(this.getTectonicEnergy() - 400);
                    world.playSound(getPosX(), getPosY(), getPosZ(), ModSounds.cyclone, SoundCategory.PLAYERS, 1.2F, 1F, true);

                }

                if (this.getCycloneTicks() > 6) {
                    player.rotationYaw--;
                    player.setRotationYawHead(player.getRotationYawHead() - 1);
                    this.applyYawToEntity(player);

                }

                if (player.getHealth() < player.getMaxHealth() * 0.5F)
                    player.heal(0.5F);

                if (this.getCycloneTicks() == 12 || this.getCycloneTicks() == 6)
                    for (LivingEntity living : getEntitiesAround(new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()), 4F, player.world)) {
                        if (living == player) {
                            continue;
                        }
                        if(getPassengers().size() > 1 && living == getPassengers().get(1))
                            continue;
                        HerrscherHandler.iceAttack(living, player,4.5F);
                        player.setLastAttackedEntity(living);
                    }

                if (getCycloneTicks() > 0) {
                    setCycloneTicks(getCycloneTicks() - 1);
                    this.deltaRotation += 5F;
                }

            }

        }else
            ridingTicks = 0;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isRemote && !this.removed) {
            if (source instanceof IndirectEntityDamageSource && source.getTrueSource() != null && this.isPassenger(source.getTrueSource())) {
                return false;
            } else {
                this.setForwardDirection(-this.getForwardDirection());
                this.setTimeSinceHit(10);
                this.markVelocityChanged();
                boolean flag = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode;
                if(source.getTrueSource() instanceof PlayerEntity)
                    this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
                if (flag || this.getDamageTaken() > 40.0F) {
                    if (!flag && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        this.entityDropItem(this.getItemStack());
                    }
                    this.remove();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public List<LivingEntity> getEntitiesAround() {
        return getEntitiesAround(new BlockPos(getPosX(), getPosY(), getPosZ()), 12F, this.world);
    }

    public static List<LivingEntity> getEntitiesAround(BlockPos source, float range, World world) {
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    public Status getBoatStatus() {
        BoatEntity.Status boatentity$status = this.getUnderwaterStatus();
        if (boatentity$status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return boatentity$status;
        } else if (this.checkInWater()) {
            return BoatEntity.Status.IN_WATER;
        } else {
            float f = this.getBoatGlide();
            if (f > 0.0F) {
                this.boatGlide =  f;
                return BoatEntity.Status.ON_LAND;
            } else {
                return BoatEntity.Status.IN_AIR;
            }
        }
    }

    @Override
    public void updateMotion() {
        double d0 = -0.03999999910593033D;
        double d1 = this.hasNoGravity() || this.spaceInputDown && this.getTectonicEnergy() >= 200 ? 0.0D : d0;
        double d2 = 0.0D;
        this.momentum = 0.05F;

        if (this.previousStatus == BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.ON_LAND) {
            this.waterLevel = this.getBoundingBox().minY + (double)this.getHeight();
            this.setPosition(this.getPosX(), (double)(this.getWaterLevelAbove() - this.getHeight()) + 0.101D, this.getPosZ());
            this.setMotion(this.getMotion().mul(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = BoatEntity.Status.IN_WATER;
        } else {
            if (this.status == BoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - this.getBoundingBox().minY + 0.1D) / (double)this.getHeight();
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_WATER) {
                d2 = 0.009999999776482582D;
                this.momentum = 0.8F;
            } else if (this.status == BoatEntity.Status.IN_AIR) {
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.ON_LAND) {
                this.momentum = 0.9F;
                if (this.getControllingPassenger() instanceof PlayerEntity) {
                    this.boatGlide /= 2.0F;
                }
            }

            Vector3d vec3d = this.getMotion();
            this.setMotion(vec3d.x * (double)this.momentum, vec3d.y + d1, vec3d.z * (double)this.momentum);
            this.deltaRotation *= this.momentum;
            if (d2 > 0.0D) {
                Vector3d vec3d1 = this.getMotion();
                this.setMotion(vec3d1.x, (vec3d1.y + d2 * 0.06153846016296973D) * 0.75D, vec3d1.z);
            }
        }
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.fallDistance = 0.0F;
    }

    @Override
    public void controlBoat() {
        if (this.isBeingRidden()) {
            float f = 0.0F;
            if (this.leftInputDown) {
                this.deltaRotation -= 1F;
            }

            if (this.rightInputDown) {
                this.deltaRotation += 1F;
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005F;
            }

            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                f += 0.05F * 1.25F;
            }

            if (this.backInputDown) {
                f -= 0.006F;
            }

            this.setMotion(this.getMotion().add((double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.world.isRemote) {
                if(player.startRiding(this))
                    world.playSound(getPosX(), getPosY(), getPosZ(), ModSounds.rideon, SoundCategory.PLAYERS, 4F, 1F, true);
                return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            } else {
                return ActionResultType.SUCCESS;
            }
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setTectonicEnergy(compound.getInt(TAG_TECTONICENERGY));
        setCycloneTicks(compound.getInt(TAG_CYCLONETICKS));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt(TAG_TECTONICENERGY, getTectonicEnergy());
        compound.putInt(TAG_CYCLONETICKS, getCycloneTicks());
    }

    public void setCycloneTicks(int i) {
        this.dataManager.set(CYCLONE_TICKS, i);
    }

    public int getCycloneTicks() {
        return this.dataManager.get(CYCLONE_TICKS);
    }

    public void setTectonicEnergy(int i) {
        this.dataManager.set(TECTONIC_ENERGY, i);
    }

    public int getTectonicEnergy() {
        return this.dataManager.get(TECTONIC_ENERGY);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
