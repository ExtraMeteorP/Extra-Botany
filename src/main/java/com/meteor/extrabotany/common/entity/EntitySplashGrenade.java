package com.meteor.extrabotany.common.entity;

import java.util.List;

import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;

public class EntitySplashGrenade extends EntityThrowableCopy{
	
	private static final String TAG_EFFECT = "effect";
	private static final DataParameter<ItemStack> ITEM = EntityDataManager.<ItemStack>createKey(EntitySplashGrenade.class, DataSerializers.ITEM_STACK);
	
	public EntitySplashGrenade(World world) {
		super(world);
	}

	public EntitySplashGrenade(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}
	
	@Override
    public float getGravityVelocity(){
        return 0.04F;
    }
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(ITEM, new ItemStack(ModItems.infinitewine));
	}
	
	@Override
	public void onUpdate() {
		
		EntityLivingBase thrower = getThrower();
		if(!world.isRemote && (thrower == null || !(thrower instanceof EntityPlayer) || thrower.isDead)) {
			setDead();
			return;
		}
		EntityPlayer player = (EntityPlayer) thrower;
		if(!world.isRemote) {
			AxisAlignedBB axis = new AxisAlignedBB(posX-0.2F, posY-0.2F, posZ-0.2F, lastTickPosX+0.2F, lastTickPosY+0.2F, lastTickPosZ+0.2F);
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
			for(EntityLivingBase living : entities) {
				if(living == thrower)
					continue;
				onImpact();
				break;
			}
			if(this.collided)
				onImpact();
		}
		
		super.onUpdate();	
	}
	
	public void onImpact(){
		if(getPotion().getItem() instanceof IBrewItem){
	    	IBrewItem bi = (IBrewItem) getPotion().getItem();	
	    	Brew brew = bi.getBrew(getPotion());
			double range = 5;
			AxisAlignedBB bounds = new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
			List<EntityLivingBase> entitiess;
			entitiess = world.getEntitiesWithinAABB(EntityLivingBase.class, bounds);
			for(EntityLivingBase living2 : entitiess){
				if(!(living2 instanceof EntityPlayer))
					living2.attackEntityFrom(DamageSource.MAGIC, 10F);
				for(PotionEffect effect : brew.getPotionEffects(getPotion())) {
					PotionEffect newEffect = new PotionEffect(effect.getPotion(), (int)((float)effect.getDuration() * 0.6F), effect.getAmplifier()+1, true, true);
					if(!(living2 instanceof EntityPlayer) && effect.getPotion().isBadEffect()){
						if(effect.getPotion().isInstant())
							effect.getPotion().affectEntity(living2, living2, living2, newEffect.getAmplifier(), 1F);
						else 
							living2.addPotionEffect(newEffect);
					}else if(living2 instanceof EntityPlayer && !effect.getPotion().isBadEffect()){
						if(effect.getPotion().isInstant())
							effect.getPotion().affectEntity(living2, living2, living2, newEffect.getAmplifier(), 1F);
						else 
							living2.addPotionEffect(newEffect);
					}
					int i = effect.getPotion().isInstant() ? 2007 : 2002;
					this.world.playEvent(i, new BlockPos(this), brew.getColor(getPotion()));
				}
			}
		}
        this.setDead();
	}

	@Override
	protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null){
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0F);
        }

        if (!this.world.isRemote){
        	onImpact();
        }
	}
	
    public void setItem(ItemStack stack){
        this.getDataManager().set(ITEM, stack);
        this.getDataManager().setDirty(ITEM);
    }
    
    public ItemStack getPotion(){
        ItemStack itemstack = (ItemStack)this.getDataManager().get(ITEM);
        return itemstack;
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound cmp){
		super.writeEntityToNBT(cmp);
        
        ItemStack itemstack = this.getPotion();

        if (!itemstack.isEmpty())
        {
            cmp.setTag("Potion", itemstack.writeToNBT(new NBTTagCompound()));
        }
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound cmp){
		super.readEntityFromNBT(cmp);
        ItemStack itemstack = new ItemStack(cmp.getCompoundTag("Potion"));

        if (itemstack.isEmpty())
        {
            this.setDead();
        }
        else
        {
            this.setItem(itemstack);
        }
	}

}
