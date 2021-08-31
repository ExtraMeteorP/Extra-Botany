package com.meteor.extrabotany.data;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockstateProvider extends BlockStateProvider {

    public BlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LibMisc.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "ExtraBotany Blockstates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> remainingBlocks = Registry.BLOCK.stream()
                .filter(b -> vazkii.botania.common.lib.LibMisc.MOD_ID.equals(Registry.BLOCK.getKey(b).getNamespace()))
                .collect(Collectors.toSet());

    }

}
