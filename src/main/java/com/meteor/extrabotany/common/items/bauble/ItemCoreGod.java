package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.common.core.handler.EquipmentHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.relic.ItemRelicBauble;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemCoreGod extends ItemRelicBauble implements IManaUsingItem {

    private static final String TAG_VARIANT = "variant";

    private static final List<String> playersWithFlight = Collections.synchronizedList(new ArrayList<>());
    private static final int COST = 35;

    private static final int SUBTYPES = 4;

    public ItemCoreGod(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::updatePlayerFlyStatus);
        MinecraftForge.EVENT_BUS.addListener(this::playerLoggedOut);
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInGroup(tab)) {
            for (int i = 0; i < SUBTYPES; i++) {
                ItemStack stack = new ItemStack(this);
                ItemNBTHelper.setInt(stack, TAG_VARIANT, i);
                list.add(stack);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        tooltip.add(new TranslationTextComponent("extrabotany.wings" + getVariant(stack)).mergeStyle(TextFormatting.GRAY));
    }

    private void updatePlayerFlyStatus(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack tiara = EquipmentHandler.findOrEmpty(this, player);

            if (playersWithFlight.contains(playerStr(player))) {
                if (shouldPlayerHaveFlight(player)) {
                    player.abilities.allowFlying = true;
                    if (player.abilities.isFlying) {
                        if (!player.world.isRemote) {
                            ManaItemHandler.instance().requestManaExact(tiara, player, COST, true);
                        } else if (Math.abs(player.getMotion().getX()) > 0.1 || Math.abs(player.getMotion().getZ()) > 0.1) {
                            double x = event.getEntityLiving().getPosX() - 0.5;
                            double y = event.getEntityLiving().getPosY() - 0.5;
                            double z = event.getEntityLiving().getPosZ() - 0.5;

                            float r = 1F;
                            float g = 1F;
                            float b = 1F;

                            int variant = getVariant(tiara);

                            switch (variant) {
                                case 0: {
                                    r = 1F;
                                    g = 0.55F;
                                    b = 0F;
                                    break;
                                }
                                case 1: {
                                    r = new float[]{0.4F, 0.98F, 0.98F, 0.98F, 0.6F, 0F, 0.15F}[player.world.rand.nextInt(7)];
                                    g = new float[]{0.82F, 0.84F, 0.52F, 0.12F, 0.21F, 0.4F, 0.98F}[player.world.rand.nextInt(7)];
                                    b = new float[]{0F, 0.18F, 0.18F, 0F, 0.98F, 0.81F, 0.82F}[player.world.rand.nextInt(7)];
                                    break;
                                }
                                case 2: {
                                    r = 0.52F;
                                    g = 0.8F;
                                    b = 0.85F;
                                    break;
                                }
                                case 3: {
                                    r = 0.95F;
                                    g = 0.7F;
                                    b = 0.38F;
                                    break;
                                }
                            }

                            for (int i = 0; i < 2; i++) {
                                SparkleParticleData data = SparkleParticleData.sparkle(2F * (float) Math.random(), r, g, b, 20);
                                player.world.addParticle(data, x + Math.random() * event.getEntityLiving().getWidth(), y + Math.random() * 0.4, z + Math.random() * event.getEntityLiving().getWidth(), 0, 0, 0);
                            }
                        }
                    }
                } else {
                    if (!player.isSpectator() && !player.abilities.isCreativeMode) {
                        player.abilities.allowFlying = false;
                        player.abilities.isFlying = false;
                        player.abilities.disableDamage = false;
                    }
                    playersWithFlight.remove(playerStr(player));
                }
            } else if (shouldPlayerHaveFlight(player)) {
                playersWithFlight.add(playerStr(player));
                player.abilities.allowFlying = true;
            }
        }
    }

    private void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        String username = event.getPlayer().getGameProfile().getName();
        playersWithFlight.remove(username + ":false");
        playersWithFlight.remove(username + ":true");
    }

    private static String playerStr(PlayerEntity player) {
        return player.getGameProfile().getName() + ":" + player.world.isRemote;
    }

    private boolean shouldPlayerHaveFlight(PlayerEntity player) {
        ItemStack armor = EquipmentHandler.findOrEmpty(this, player);
        if (!armor.isEmpty()) {
            return ManaItemHandler.instance().requestManaExact(armor, player, COST, false);
        }
        return false;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity player) {
        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;

        }
    }

    @Override
    public boolean hasRender(ItemStack stack, LivingEntity living) {
        return super.hasRender(stack, living) && living instanceof PlayerEntity;
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderHerrscher(BipedModel<?> bipedModel, IBakedModel model, ItemStack stack, MatrixStack ms, IRenderTypeBuffer buffers, float flap) {
        ms.push();
        bipedModel.bipedBody.translateRotate(ms);
        ms.translate(0, -0.2, 0.3);


        for(int i = 0; i < 3; i++) {
            ms.push();
            ms.rotate(Vector3f.YP.rotationDegrees(flap*0.25F));
            ms.rotate(Vector3f.ZP.rotationDegrees(-35F * i));
            IBakedModel model_ = MiscellaneousIcons.INSTANCE.coregodModel[0];
            ms.translate(-1.2, -0.1F * i, 0);

            ms.scale(1.9F, -1.9F, -1.9F);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, false, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY, model_);
            ms.pop();
        }

        ms.push();
        ms.rotate(Vector3f.YP.rotationDegrees(180 - flap*0.25F));

        ms.translate(-1.2, 0, 0);

        ms.scale(1.7F, -1.7F, -1.7F);
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, false, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY, model);
        ms.pop();


        ms.pop();
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderBasic(BipedModel<?> bipedModel, IBakedModel model, ItemStack stack, MatrixStack ms, IRenderTypeBuffer buffers, int light, float flap) {
        ms.push();

        // attach to body
        bipedModel.bipedBody.translateRotate(ms);

        // position on body
        ms.translate(0, 0.2, 0.2);

        for (int i = 0; i < 2; i++) {
            ms.push();
            ms.rotate(Vector3f.YP.rotationDegrees(i == 0 ? flap : 180 - flap));

            // move so flapping about the edge instead of center of texture
            ms.translate(-1, 0, 0);

            ms.scale(1.5F, -1.5F, -1.5F);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, false, ms, buffers, light, OverlayTexture.NO_OVERLAY, model);
            ms.pop();
        }

        ms.pop();
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void doRender(BipedModel<?> bipedModel, ItemStack stack, LivingEntity living, MatrixStack ms, IRenderTypeBuffer buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        int meta = getVariant(stack);
        if (meta < 0 || meta >= MiscellaneousIcons.INSTANCE.coregodWingsModel.length + 1) {
            return;
        }

        IBakedModel model = MiscellaneousIcons.INSTANCE.coregodWingsModel[meta];
        boolean flying = living instanceof PlayerEntity && ((PlayerEntity) living).abilities.isFlying;
        float flap = 12F + (float) ((Math.sin((double) (living.ticksExisted + partialTicks) * (flying ? 0.2F : 0.12F)) + 0.4F) * (flying ? 30F : 5F));

        switch (meta) {
            case 0:
                renderHerrscher(bipedModel, model, stack, ms, buffers, flap);
                break;
            case 1:
                renderBasic(bipedModel, model, stack, ms, buffers, light, flap*0.25F);
                break;
            case 2:
            case 3:
                renderBasic(bipedModel, model, stack, ms, buffers, light, flap);
                break;
        }
    }

    public static int getVariant(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_VARIANT, 0);
    }

}
