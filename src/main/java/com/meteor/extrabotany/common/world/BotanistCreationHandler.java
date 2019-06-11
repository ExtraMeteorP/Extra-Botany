package com.meteor.extrabotany.common.world;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class BotanistCreationHandler implements IVillageCreationHandler{

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int size) {
		return new PieceWeight(ComponentBotanist.class, 3, MathHelper.getInt(random, size, 1 + size));
	}

	@Override
	public Class<?> getComponentClass() {
		return ComponentBotanist.class;
	}

    @Nullable
    @Override
    public StructureVillagePieces.Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int x, int y, int z, EnumFacing facing, int type) {
        return ComponentBotanist.buildComponent(startPiece, pieces, random, x, y, z, facing, type);
    }

}
