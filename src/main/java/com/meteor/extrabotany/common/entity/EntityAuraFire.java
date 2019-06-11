package com.meteor.extrabotany.common.entity;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;

public class EntityAuraFire extends EntityThrowableCopy{
	
	public EntityAuraFire(World world) {
		super(world);
		setSize(0F, 0F);
	}

	public EntityAuraFire(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		setSize(0F, 0F);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		setSize(0F, 0F);
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}
	
	@Override
	protected float getGravityVelocity(){
		return 0F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.ticksExisted > 80)
			this.setDead();
		if(this.world.isRemote)
			for(int i = 0; i < 5; i++)
				this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + Math.random() * 0.4F - 0.2F, this.posY + Math.random() * 0.4F - 0.2F, this.posZ + Math.random() * 0.4F - 0.2F, 0, 0, 0);
	}

	@Override
	protected void onImpact(RayTraceResult pos) {
		if(this.thrower != null && this.thrower instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) this.thrower;
			double attribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
			if(pos.entityHit != null || pos.entityHit != thrower) {
				if(pos.entityHit instanceof EntityLivingBase){
					float dmg = (float) (4 + attribute);
					((EntityLivingBase)pos.entityHit).attackEntityFrom(DamageSource.causePlayerDamage(player), dmg);
					player.setAbsorptionAmount(Math.min(10, player.getAbsorptionAmount() + 1F));
					ManaItemHandler.requestManaExactForTool(new ItemStack(Items.APPLE), player, 300, true);
					if(dmg >= 40)
						ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ONEPUCHMAN);
					this.setDead();
				}
			}
		}
	}

}
