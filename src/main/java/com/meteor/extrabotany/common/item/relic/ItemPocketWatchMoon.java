package com.meteor.extrabotany.common.item.relic;

import java.util.List;

import com.meteor.extrabotany.common.entity.EntityFlyCutter;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemPocketWatchMoon extends ItemModRelic{

	public ItemPocketWatchMoon() {
		super(LibItemsName.POCKETWATCHMOON);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
		ItemStack stack = player.getHeldItem(handIn);
		if(!player.isSneaking()){
			EntityFlyCutter sg = new EntityFlyCutter(world);
			sg.setDamage(6);
			sg.setProps(player);
			sg.setNoGravity(true);
			sg.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
			sg.rotationYaw = player.rotationYaw;
			sg.setRotation(MathHelper.wrapDegrees(-player.rotationYaw + 180));
			sg.setVariety(world.rand.nextInt(7));
			sg.setDelay(true);
			sg.setPitch(-player.rotationPitch);
			sg.setPosition(player.posX, player.posY + 1F, player.posZ);
	
			if(!world.isRemote)
				world.spawnEntity(sg);
		}else{
			BlockPos source = player.getPosition();
			float range = 30F;
			List<EntityFlyCutter> cutters = world.getEntitiesWithinAABB(EntityFlyCutter.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
			for(EntityFlyCutter cutter : cutters)
				cutter.setDelay(false);
			
			if(!player.isPotionActive(MobEffects.ABSORPTION))
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 300, 3));
		}
	    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

}
