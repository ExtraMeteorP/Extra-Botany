package com.meteor.extrabotany.common.item.relic;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.entity.IBossProjectile;
import com.meteor.extrabotany.api.entity.IEntityWithShield;
import com.meteor.extrabotany.common.entity.EntitySubspace;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemCamera extends ItemModRelic implements IManaUsingItem {

	int range = 20;

	public ItemCamera() {
		super(LibItemsName.CAMERA);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (isRightPlayer(player, stack) && ManaItemHandler.requestManaExactForTool(stack, player, 1500, true)
				&& !world.isRemote) {
			for (EntityLiving living : player.getEntityWorld().getEntitiesWithinAABB(EntityLiving.class,
					new AxisAlignedBB(player.getPosition().add(-range, -range, -range),
							player.getPosition().add(range + 1, range + 1, range + 1)))) {
				if (living.isSpectatedByPlayer((EntityPlayerMP) player)) {
					living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 5));
					int time = 200;
					if (!living.isNonBoss())
						time = 40;
					if (living.getEntityData().getInteger("freezeTimes") > 10)
						time = 0;
					living.getEntityData().setInteger("freezeTime", time);
					living.getEntityData().setInteger("freezeTimes",
							living.getEntityData().getInteger("freezeTimes") + 1);
				}
			}

			for (Entity e : player.getEntityWorld().getEntitiesWithinAABB(Entity.class,
					new AxisAlignedBB(player.getPosition().add(-range, -range, -range),
							player.getPosition().add(range + 1, range + 1, range + 1)))) {
				if (e instanceof EntitySubspace)
					continue;

				if (e instanceof IBossProjectile) {
					if (((IBossProjectile) e).isBoss(e))
						continue;
				}

				if (e instanceof IProjectile)
					e.setDead();
			}
			player.getCooldownTracker().setCooldown(stack.getItem(), 200);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event) {

		if (event.getEntityLiving() instanceof IEntityWithShield) {
			EntityLiving living = (EntityLiving) event.getEntityLiving();
			IEntityWithShield e = (IEntityWithShield) event.getEntityLiving();
			if (e.getShield() > 0) {
				e.setShield(e.getShield() - 1);
				event.setAmount(0);
			}
		}

	}

	@Override
	public boolean usesMana(ItemStack arg0) {
		return true;
	}

}
