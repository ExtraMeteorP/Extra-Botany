package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class EntitySplashGrenade extends ThrowableEntity implements IRendersAsItem{

    private static final DataParameter<ItemStack> ITEM = EntityDataManager
            .createKey(EntitySplashGrenade.class, DataSerializers.ITEMSTACK);

    private PlayerEntity thrower;

    public EntitySplashGrenade(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EntitySplashGrenade(World worldIn, PlayerEntity thrower) {
        super(ModEntities.SPLASHGRENADE, worldIn);
        this.thrower = thrower;
    }

    @Override
    public void tick() {

        if (!world.isRemote && (thrower == null || thrower.removed)) {
            remove();
            return;
        }

        if (!world.isRemote) {
            AxisAlignedBB axis = new AxisAlignedBB(getPosX() - 0.2F, getPosY() - 0.2F, getPosZ() - 0.2F, lastTickPosX + 0.2F,
                    lastTickPosY + 0.2F, lastTickPosZ + 0.2F);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, axis);
            for (LivingEntity living : entities) {
                if(living == thrower)
                    continue;
                onImpact();
                break;
            }
        }

        super.tick();
    }

    public void onImpact() {
        if (getPotion().getItem() instanceof IBrewItem) {
            IBrewItem bi = (IBrewItem) getPotion().getItem();
            Brew brew = bi.getBrew(getPotion());
            double range = 5;
            AxisAlignedBB bounds = new AxisAlignedBB(getPosX() - range, getPosY() - range, getPosZ() - range, getPosX() + range,
                    getPosY() + range, getPosZ() + range);
            List<LivingEntity> entitiess;
            entitiess = world.getEntitiesWithinAABB(LivingEntity.class, bounds);
            for (LivingEntity living2 : entitiess) {
                if (!(living2 instanceof PlayerEntity))
                    living2.attackEntityFrom(DamageSource.MAGIC, 10F);
                for (EffectInstance effect : brew.getPotionEffects(getPotion())) {
                    EffectInstance newEffect = new EffectInstance(effect.getPotion(),
                            (int) ((float) effect.getDuration() * 0.6F), effect.getAmplifier(), true, true);
                    if (!(living2 instanceof PlayerEntity) && !effect.getPotion().isBeneficial()) {
                        if (effect.getPotion().isInstant())
                            effect.getPotion().affectEntity(living2, living2, living2, newEffect.getAmplifier(), 1F);
                        else
                            living2.addPotionEffect(newEffect);
                    } else if (living2 instanceof PlayerEntity && effect.getPotion().isBeneficial()) {
                        if (effect.getPotion().isInstant())
                            effect.getPotion().affectEntity(living2, living2, living2, newEffect.getAmplifier(), 1F);
                        else
                            living2.addPotionEffect(newEffect);
                    }
                    int i = effect.getPotion().isInstant() ? 2007 : 2002;
                    this.world.playEvent(i, this.getPosition(), brew.getColor(getPotion()));
                }
            }
        }
        this.remove();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        result.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), 5.0F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            onImpact();
        }
    }

    @Override
    public float getGravityVelocity() {
        return 0.02F;
    }

    @Override
    public void registerData() {
        dataManager.register(ITEM, ItemStack.EMPTY);
    }

    public void setItem(ItemStack stack) {
        this.getDataManager().set(ITEM, Util.make(stack.copy(), (p_213883_0_) -> {
            p_213883_0_.setCount(1);
        }));
    }

    public ItemStack getPotion() {
        ItemStack itemstack = this.getDataManager().get(ITEM);
        return itemstack;
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        ItemStack itemstack = this.getPotion();
        if (!itemstack.isEmpty()) {
            cmp.put("Potion", itemstack.write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
        ItemStack itemstack = ItemStack.read(cmp.getCompound("Potion"));
        this.setItem(itemstack);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public ItemStack getItem() {
        return getPotion();
    }
}
