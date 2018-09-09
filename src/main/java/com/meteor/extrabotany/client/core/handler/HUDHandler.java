package com.meteor.extrabotany.client.core.handler;

import java.util.List;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class HUDHandler{

	private HUDHandler() {}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onDrawScreenPre(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		Profiler profiler = mc.mcProfiler;
	
		if(event.getType() == ElementType.HEALTH) {
			profiler.startSection("extrabotany-hud");
	
			profiler.endSection();
		}
	}

	@SubscribeEvent
	public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		Profiler profiler = mc.mcProfiler;
		ItemStack main = mc.player.getHeldItemMainhand();
		ItemStack offhand = mc.player.getHeldItemOffhand();
	
		if(event.getType() == ElementType.ALL) {
			profiler.startSection("extrabotany-hud");
			World world = mc.player.getEntityWorld();
			BlockPos pos = mc.player.getPosition();
			int range = 1;
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range)));
			
			boolean looking = false;
			boolean check = false;
			RayTraceResult ray = null;

			for(EntityPlayer player : players){ 
				
				RayTraceResult posi = ToolCommons.raytraceFromEntity(world, player, true, 6);
				if(posi != null && posi.getBlockPos() != null && posi.typeOfHit == RayTraceResult.Type.BLOCK) {
					looking = true;
					ray = posi;
					break;
				}
				
			}	
			
			TileEntity tileEntity = null;
			if(looking && ray != null){
				 BlockPos newpos = ray.getBlockPos();
				 tileEntity = world.getTileEntity(newpos);
				 check = true;
			}
								
			if(check && tileEntity != null){
				if(tileEntity instanceof TilePool){
					TilePool pool = (TilePool) tileEntity;
					int mana = pool.getCurrentMana();
					int maxmana = pool.manaCap;
					GlStateManager.pushMatrix();
					boolean unicode = mc.fontRenderer.getUnicodeFlag();
					mc.fontRenderer.setUnicodeFlag(true);
					mc.fontRenderer.drawStringWithShadow(mana + "/" + maxmana, 160 + 15, 12 + 4, 0xFFFFFF);
					mc.fontRenderer.setUnicodeFlag(unicode);
					GlStateManager.popMatrix();
				}
				
				if(tileEntity instanceof TileSpreader){
					TileSpreader pool = (TileSpreader) tileEntity;
					int mana = pool.getUpdateTag().getInteger("knownMana");
					int maxmana = pool.getMaxMana();
					
					GlStateManager.pushMatrix();
					boolean unicode = mc.fontRenderer.getUnicodeFlag();
					mc.fontRenderer.setUnicodeFlag(true);
					mc.fontRenderer.drawStringWithShadow(mana + "/" + maxmana, 160 + 15, 12 + 4, 0xFFFFFF);
					mc.fontRenderer.setUnicodeFlag(unicode);
					GlStateManager.popMatrix();
				}
			}

			
			profiler.endSection();
			GlStateManager.color(1F, 1F, 1F, 1F);
		}
	}
	
	public RayTraceResult rayTrace(Entity entity, double playerReach, float partialTicks) {
        Vec3d eyePosition = entity.getPositionEyes(partialTicks);
        Vec3d lookVector = entity.getLook(partialTicks);
        Vec3d traceEnd = eyePosition.addVector(lookVector.x * playerReach, lookVector.y * playerReach, lookVector.z * playerReach);

        return entity.getEntityWorld().rayTraceBlocks(eyePosition, traceEnd, true);
    }
}
