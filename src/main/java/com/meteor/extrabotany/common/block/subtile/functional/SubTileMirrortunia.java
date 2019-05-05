package com.meteor.extrabotany.common.block.subtile.functional;

import com.meteor.extrabotany.api.subtile.SubTileFunctionalNature;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.item.equipment.tool.ItemNatureOrb;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;

import java.util.List;

public class SubTileMirrortunia extends SubTileFunctionalNature{
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		float range = 13F;
		AxisAlignedBB axis = new AxisAlignedBB(getPos().getX()-range, getPos().getY()-range, getPos().getZ()-range, getPos().getX()+range, getPos().getY()+range, getPos().getZ()+range);
		List<EntityPlayer> entities = getWorld().getEntitiesWithinAABB(EntityPlayer.class, axis);
		for(EntityPlayer player : entities){
			if(mana >= 90 && ticksExisted % 50 == 0){
				mana-=90;
				if(isEnabled())
					player.addPotionEffect(new PotionEffect(ModPotions.reflect, 120, 4));
				else
					player.addPotionEffect(new PotionEffect(ModPotions.reflect, 120, 3));
				ItemNatureOrb.clearPotions(player);
			}
		}
		
	}
	
	@Override
	public int getRate(){
		return 20;
	}
	
	@Override
	public boolean willConsume(){
		return true;
	}
	
	@Override
	public int getColor() {
		return 0X4169E1;
	}

	@Override
	public int getMaxMana() {
		return 2000;
	}
	
	@Override
	public boolean acceptsRedstone() {
		return true;
	}
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.mirrortunia;
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), 13);
	}

}
