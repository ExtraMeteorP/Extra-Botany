package com.meteor.extrabotany.common.item;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.entity.EntityFlyingBoat;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlyingBoat extends ItemMod{
	
	final int types = 3;

	public ItemFlyingBoat() {
		super(LibItemsName.FLYINGBOAT);
		setHasSubtypes(true);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemsName.FLYING_BOAT_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}
	
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab)) 
			for(int i = 0; i < types; i++) 
				stacks.add(new ItemStack(this, 1, i));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		for (int i = 0; i < LibItemsName.FLYING_BOAT_NAMES.length; i++)
			if (!"UNUSED".equals(LibItemsName.FLYING_BOAT_NAMES[i])) 
				ModelLoader.setCustomModelResourceLocation(
					this, i,
					new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.FLYING_BOAT_NAMES[i], "inventory")
				);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        float f = 1.0F;
        float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * 1.0F;
        float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * 1.0F;
        double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * 1.0D;
        double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * 1.0D + (double)playerIn.getEyeHeight();
        double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * 1.0D;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3d vec3d1 = vec3d.addVector((double)f7 * 5.0D, (double)f6 * 5.0D, (double)f8 * 5.0D);
        RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);

        if (raytraceresult == null){
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else{
            Vec3d vec3d2 = playerIn.getLook(1.0F);
            boolean flag = false;
            List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D).grow(1.0D));

            for (int i = 0; i < list.size(); ++i){
                Entity entity = list.get(i);

                if (entity.canBeCollidedWith()){
                    AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow((double)entity.getCollisionBorderSize());

                    if (axisalignedbb.contains(vec3d)){
                        flag = true;
                    }
                }
            }

            if (flag){
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            }
            else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK){
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            }
            else{
                Block block = worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
                boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
                EntityFlyingBoat entityboat = new EntityFlyingBoat(worldIn, raytraceresult.hitVec.x, flag1 ? raytraceresult.hitVec.y - 0.12D : raytraceresult.hitVec.y, raytraceresult.hitVec.z);
                entityboat.rotationYaw = playerIn.rotationYaw;
                entityboat.setRaftType(EntityFlyingBoat.Type.byId(this.getMetadata(itemstack)));

                if (!worldIn.getCollisionBoxes(entityboat, entityboat.getEntityBoundingBox().grow(-0.1D)).isEmpty()){
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                }
                else{
                    if (!worldIn.isRemote){
                        worldIn.spawnEntity(entityboat);
                    }

                    if (!playerIn.capabilities.isCreativeMode){
                        itemstack.shrink(1);
                    }

                    playerIn.addStat(StatList.getObjectUseStats(this));
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }
        }
    }

}
