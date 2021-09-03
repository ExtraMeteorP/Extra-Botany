package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.IPoolOverlayProvider;

public class BlockDimensionCatalyst extends Block implements IPoolOverlayProvider {

    public BlockDimensionCatalyst(Properties builder) {
        super(builder);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getIcon(World world, BlockPos pos) {
        return MiscellaneousIcons.INSTANCE.dimensionCatalystOverlay.getSprite();
    }
}
