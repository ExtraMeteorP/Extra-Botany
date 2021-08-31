package com.meteor.extrabotany.common.items.bauble;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;

public class ItemManaDriveRing extends ItemBauble implements IManaUsingItem {

    public ItemManaDriveRing(Properties props) {
        super(props);
    }

    private static final int RANGE = 7;

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(!(entity instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) entity;
        if(player.ticksExisted % 20 == 0)
            for(int x = -RANGE; x <= RANGE; x++)
                for(int y = -RANGE; y <= RANGE; y++)
                    for(int z = -RANGE; z <= RANGE; z++) {
                        TileEntity te = player.getEntityWorld().getTileEntity(new BlockPos(player.getPosition().add(x, y, z)));
                        if(te instanceof TileEntityFunctionalFlower) {
                            TileEntityFunctionalFlower f = (TileEntityFunctionalFlower) te;
                            int manaToUse = f.getMaxMana() - f.getMana();
                            if(ManaItemHandler.instance().requestManaExact(stack, player, manaToUse, true))
                                f.addMana(manaToUse);
                        }
                    }
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
