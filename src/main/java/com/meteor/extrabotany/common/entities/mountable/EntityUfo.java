package com.meteor.extrabotany.common.entities.mountable;

import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityUfo extends EntityMountable {

    private static final String TAG_CATCHED_ID = "catched_id";

    private static final DataParameter<Integer> CATCHED_ID = EntityDataManager.createKey(EntityUfo.class,
            DataSerializers.VARINT);

    public EntityUfo(EntityType<? extends EntityUfo> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
    }

    public EntityUfo(World worldIn) {
        super(ModEntities.UFO, worldIn);
    }

    public EntityUfo(World worldIn, double x, double y, double z) {
        super(ModEntities.UFO, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(0, 0, 0);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.setNoGravity(true);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CATCHED_ID, -1);
    }

    @Override
    public double getMountedYOffset() {
        return 1-0.395D;
    }

    @Override
    public Item getItemBoat() {
        return ModItems.cosmiccarkey;
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = null;
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof PlayerEntity) {
            player = (PlayerEntity) this.getPassengers().get(0);

            float speed = 0.75F;
            double mx = (double) (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * speed);
            double mz = (double) (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
                    * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * speed);
            double my = 0;

            Vector3d f0 = new Vector3d(0, 0, 0);
            Vector3d vecf = new Vector3d(mx, my, mz);
            Vector3d vecl = new Vector3d(mx, my, mz).rotateYaw((float) (Math.PI * 0.5D)).scale(0.75F);
            Vector3d vecr = new Vector3d(mx, my, mz).rotateYaw(-(float) (Math.PI * 0.5D)).scale(0.75F);
            Vector3d vecb = new Vector3d(mx, my, mz).rotateYaw((float) (Math.PI)).scale(0.6F);

            if(this.forwardInputDown) {
                f0 = f0.add(vecf);
            }
            if(this.leftInputDown) {
                f0 = f0.add(vecl);
            }
            if(this.rightInputDown) {
                f0 = f0.add(vecr);
            }
            if(this.backInputDown) {
                f0 = f0.add(vecb);
            }
            if(this.spaceInputDown)
                f0 = f0.add(0, 0.35D, 0);
            else if(this.ctrlInputDown)
                f0 = f0.add(0, -0.35D, 0);

            if(f0.length() != 0)
                this.rotationYaw = getRotationFromVector(player.rotationYaw, f0.normalize());

            this.setMotion(f0);

        }

        if(getCatchedID() != -1){
            Entity entity = world.getEntityByID(this.getCatchedID());
            if(entity == null || entity.removed || entity.getDistance(this) >= 16F)
                setCatchedID(-1);
            else{
                entity.setMotion(new Vector3d(getPosX() - entity.getPosX(), getPosY() - 2F - entity.getPosY(), getPosZ() - entity.getPosZ()).normalize().scale(0.75F));
                if(this.spaceInputDown) {
                    entity.setPosition(entity.getPosX(), entity.getPosY() + 0.33D, entity.getPosZ());
                }
                else if(this.ctrlInputDown) {
                    entity.setPosition(entity.getPosX(), entity.getPosY() - 0.37D, entity.getPosZ());
                }
                entity.fallDistance = 0;
            }
        }
    }

    public static float getRotationFromVector(float rot, Vector3d vec){
        double f2 = vec.z;
        double f3 = vec.x;
        double f12 = Math.asin(f3);
        double f13 = Math.acos(f2);
        double yawx = -(f13 / ((float)Math.PI / 180F));
        double yawz = -(f12 / ((float)Math.PI / 180F));
        return MathHelper.wrapDegrees(rot) >= 0 ? (float) -yawx : (float) yawx;
    }

    public List<LivingEntity> getEntitiesBelow() {
        return getEntitiesBelow(new BlockPos(getPosX(), getPosY(), getPosZ()), this.world);
    }

    public static List<LivingEntity> getEntitiesBelow(BlockPos source, World world) {
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 2F, source.getY() - 0.5F, source.getZ() + 2F,
                        source.getX() -1.5F, source.getY() - 16F, source.getZ() -1.5F));
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
        double d1 = 0.0D;
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

    }

    @Override
    protected void applyYawToEntity(Entity entityToUpdate) {
        this.setRenderYawOffset(entityToUpdate.rotationYaw);
        float f = MathHelper.wrapDegrees(this.rotationYaw - entityToUpdate.rotationYaw);
        float f1 = MathHelper.clamp(f, -180.0F, 180.0F);
        this.prevRotationYaw += f1 - f;
        this.rotationYaw += f1 - f;
        this.setRotationYawHead(this.rotationYaw);
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.world.isRemote) {
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
        setCatchedID(compound.getInt(TAG_CATCHED_ID));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt(TAG_CATCHED_ID, getCatchedID());
    }

    public void setCatchedID(int i) {
        this.dataManager.set(CATCHED_ID, i);
    }

    public int getCatchedID() {
        return this.dataManager.get(CATCHED_ID);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
