package com.meteor.extrabotany.common.core;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class Helper {

    public static Vector3d PosToVec(BlockPos pos){
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

}
