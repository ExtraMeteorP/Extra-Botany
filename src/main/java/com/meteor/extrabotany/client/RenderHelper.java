package com.meteor.extrabotany.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RenderHelper {

    public static void renderItemCustomColor(LivingEntity entity, ItemStack stack, int color, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay, @Nullable IBakedModel model) {
        ms.push();
        if (model == null) {
            model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(stack, entity.world, entity);
        }
        model = ForgeHooksClient.handleCameraTransforms(ms, model, ItemCameraTransforms.TransformType.NONE, false);
        ms.translate(-0.5D, -0.5D, -0.5D);

        if (!model.isBuiltInRenderer() && (stack.getItem() != Items.TRIDENT)) {
            RenderType rendertype = RenderTypeLookup.func_239219_a_(stack, true);
            IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(buffers, rendertype, true, stack.hasEffect());
            renderBakedItemModel(model, stack, color, light, overlay, ms, ivertexbuilder);
        } else {
            stack.getItem().getItemStackTileEntityRenderer().func_239207_a_(stack, ItemCameraTransforms.TransformType.NONE, ms, buffers, light, overlay);
        }

        ms.pop();
    }

    public static void renderItemCustomColor(LivingEntity entity, ItemStack stack, int color, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        renderItemCustomColor(entity, stack, color, ms, buffers, light, overlay, null);
    }

    // [VanillaCopy] ItemRenderer with custom color
    private static void renderBakedItemModel(IBakedModel model, ItemStack stack, int color, int light, int overlay, MatrixStack ms, IVertexBuilder buffer) {
        Random random = new Random();
        long i = 42L;

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderBakedItemQuads(ms, buffer, color, model.getQuads((BlockState) null, direction, random), stack, light, overlay);
        }

        random.setSeed(42L);
        renderBakedItemQuads(ms, buffer, color, model.getQuads((BlockState) null, (Direction) null, random), stack, light, overlay);
    }

    // [VanillaCopy] ItemRenderer, with custom color + alpha support
    private static void renderBakedItemQuads(MatrixStack ms, IVertexBuilder buffer, int color, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
        MatrixStack.Entry matrixstack$entry = ms.getLast();

        for (BakedQuad bakedquad : quads) {
            int i = color;

            float f = (float) (i >> 16 & 255) / 255.0F;
            float f1 = (float) (i >> 8 & 255) / 255.0F;
            float f2 = (float) (i & 255) / 255.0F;
            float alpha = ((color >> 24) & 0xFF) / 255.0F;
            buffer.addVertexData(matrixstack$entry, bakedquad, f, f1, f2, alpha, light, overlay, true);
        }

    }

}
