package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ICosmeticBauble;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.helper.IconHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemCoreGod extends ItemBaubleRelic implements IManaUsingItem, ICosmeticBauble {

    public static final List<String> playersWithFlight = new ArrayList();

    private static final int COST = 25;
    final int types = 2;

    public ItemCoreGod() {
        super(LibItemsName.BAUBLE_COREGOD);
        MinecraftForge.EVENT_BUS.register(this);
        setHasSubtypes(true);
    }

    public static String playerStr(EntityPlayer player) {
        return player.getGameProfile().getName() + ":" + player.world.isRemote;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < types; i++) {
                stacks.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);
        if (player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;
            boolean flying = p.capabilities.isFlying;

            if (flying) {
                if (player instanceof EntityPlayerMP)
                    BotaniaAPI.internalHandler.sendBaubleUpdatePacket((EntityPlayerMP) player, 5);
            }

        }
    }

    @SubscribeEvent
    public void updatePlayerFlyStatus(LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack core = BaublesApi.getBaublesHandler(player).getStackInSlot(5);

            if (playersWithFlight.contains(playerStr(player))) {
                if (shouldPlayerHaveFlight(player)) {
                    player.capabilities.allowFlying = true;
                    if (player.capabilities.isFlying) {
                        if (!player.world.isRemote)
                            ManaItemHandler.requestManaExact(core, player, COST, true);
                    }
                } else {
                    if (!player.isSpectator() && !player.capabilities.isCreativeMode) {
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        player.capabilities.disableDamage = false;
                    }
                    playersWithFlight.remove(playerStr(player));
                }
            } else if (shouldPlayerHaveFlight(player)) {
                playersWithFlight.add(playerStr(player));
                player.capabilities.allowFlying = true;
            }
        }
    }

    @SubscribeEvent
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        String username = event.player.getGameProfile().getName();
        playersWithFlight.remove(username + ":false");
        playersWithFlight.remove(username + ":true");
    }

    private boolean shouldPlayerHaveFlight(EntityPlayer player) {
        ItemStack armor = BaublesApi.getBaublesHandler(player).getStackInSlot(5);
        if (!armor.isEmpty() && armor.getItem() == this) {
            return ManaItemHandler.requestManaExact(armor, player, COST, false);
        }

        return false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        if (type == RenderType.BODY) {
            TextureAtlasSprite icon = null;

            int meta = stack.getMetadata();

            boolean flying = player.capabilities.isFlying;

            float rz = 120F;
            float rx = 20F + (float) ((Math.sin((double) (player.ticksExisted + partialTicks) * (flying ? 0.3F : 0.2F)) + 0.5F) * (flying ? 15F : 5F));
            float ry = 0F;
            float h = 0.2F;
            float i = 0.15F;
            float s = 1F;
            float x = 0F;

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1F, 1F, 1F, 1F);

            int light = 15728880;
            int lightmapX = light % 65536;
            int lightmapY = light / 65536;

            float lbx = OpenGlHelper.lastBrightnessX;
            float lby = OpenGlHelper.lastBrightnessY;

            switch (meta) {
                case 0: {
                    rz = 170F;
                    h = 1F;
                    rx = 0F;
                    ry = -(float) ((Math.sin((double) (player.ticksExisted + partialTicks) * (flying ? 0.3F : 0.2F)) + 0.6F) * (flying ? 15F : 5F));
                    icon = MiscellaneousIcons.INSTANCE.godcoreIcons[0];
                    break;
                }
                case 1: {
                    rz = 180F;
                    h = 1F;
                    i = 0.22F;
                    x = 0.16F;
                    rx = 0F;
                    ry = -(float) ((Math.sin((double) (player.ticksExisted + partialTicks) * (0.2F)) + 0.6F) * (3F));
                    icon = MiscellaneousIcons.INSTANCE.volantoroIcon;
                    break;
                }
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
            float mul = 32F / 20F;
            s *= mul;

            float f = icon.getMinU();
            float f1 = icon.getMaxU();
            float f2 = icon.getMinV();
            float f3 = icon.getMaxV();
            float sr = 1F / s;

            Helper.rotateIfSneaking(player);

            GlStateManager.translate(0F, h, i);

            GlStateManager.pushMatrix();
            GlStateManager.translate(-x, 0F, 0F);
            GlStateManager.rotate(rz, 0F, 0F, 1F);
            GlStateManager.rotate(rx, 1F, 0F, 0F);
            GlStateManager.rotate(ry, 0F, 1F, 0F);
            GlStateManager.scale(s, s, s);
            IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
            GlStateManager.scale(sr, sr, sr);
            GlStateManager.rotate(-ry, 0F, 1F, 0F);
            GlStateManager.rotate(-rx, 1F, 0F, 0F);
            GlStateManager.rotate(-rz, 0F, 0F, 1F);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(x, 0F, 0F);
            switch (meta) {
                case 0:
                    TextureAtlasSprite icon2 = MiscellaneousIcons.INSTANCE.godcoreIcons[1];
                    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    GlStateManager.scale(-1F, 1F, 1F);
                    GlStateManager.rotate(rz, 0F, 0F, 1F);
                    GlStateManager.rotate(rx, 1F, 0F, 0F);
                    GlStateManager.rotate(ry, 0F, 1F, 0F);
                    GlStateManager.scale(s, s, s);
                    IconHelper.renderIconIn3D(Tessellator.getInstance(), icon2.getMaxU(), icon2.getMinV(), icon2.getMinU(), icon2.getMaxV(), icon2.getIconWidth(), icon2.getIconHeight(), 1F / 32F);
                    GlStateManager.scale(sr, sr, sr);

                    GlStateManager.pushMatrix();
                    TextureAtlasSprite icon3 = MiscellaneousIcons.INSTANCE.godcoreIcons[2];
                    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    GlStateManager.scale(s, s, s);
                    GlStateManager.color(0.85F, 0.42F, 0.02F, Math.max(0.2F, Math.min(1F, 0.45F + (float) Math.sin((double) (player.ticksExisted + partialTicks) * (flying ? 0.4F : 0.2F)))));
                    IconHelper.renderIconIn3D(Tessellator.getInstance(), icon3.getMaxU(), icon3.getMinV(), icon3.getMinU(), icon3.getMaxV(), icon3.getIconWidth(), icon3.getIconHeight(), 1F / 32F);
                    GlStateManager.scale(sr, sr, sr);
                    GlStateManager.popMatrix();

                    GlStateManager.rotate(-ry, 1F, 0F, 0F);
                    GlStateManager.rotate(-rx, 1F, 0F, 0F);
                    GlStateManager.rotate(-rz, 0F, 0F, 1F);
                    break;
                case 1:
                    GlStateManager.scale(-1F, 1F, 1F);
                    GlStateManager.rotate(rz, 0F, 0F, 1F);
                    GlStateManager.rotate(rx, 1F, 0F, 0F);
                    GlStateManager.rotate(ry, 0F, 1F, 0F);
                    GlStateManager.scale(s, s, s);
                    IconHelper.renderIconIn3D(Tessellator.getInstance(), icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
                    GlStateManager.scale(sr, sr, sr);
                    GlStateManager.rotate(-ry, 1F, 0F, 0F);
                    GlStateManager.rotate(-rx, 1F, 0F, 0F);
                    GlStateManager.rotate(-rz, 0F, 0F, 1F);
                    break;
            }
            GlStateManager.popMatrix();

            GlStateManager.color(1F, 1F, 1F);
            GlStateManager.popMatrix();

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
        }
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
        if (!player.world.isRemote) {
            Multimap<String, AttributeModifier> attributes = HashMultimap.create();
            fillModifiers(attributes, stack);
            player.getAttributeMap().applyAttributeModifiers(attributes);
        }
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        if (!player.world.isRemote) {
            Multimap<String, AttributeModifier> attributes = HashMultimap.create();
            fillModifiers(attributes, stack);
            player.getAttributeMap().removeAttributeModifiers(attributes);
        }
    }

    void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {
        if (stack.isEmpty())
            return;
        attributes.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(getBaubleUUID(stack), "Core God", 0.25F, 1).setSaved(false));
        attributes.put(SharedMonsterAttributes.FLYING_SPEED.getName(), new AttributeModifier(getBaubleUUID(stack), "Core God", 0.6F, 1).setSaved(false));
        attributes.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(getBaubleUUID(stack), "Core God", 0.25F, 1).setSaved(false));
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BODY;
    }

    @Override
    public boolean usesMana(ItemStack arg0) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        for (int i = 0; i < types; i++) {
            ModelLoader.setCustomModelResourceLocation(
                    this, i,
                    new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.BAUBLE_COREGOD, "inventory")
            );
        }
    }

    @SideOnly(Side.CLIENT)
    public void addBindInfo(List<String> list, ItemStack stack) {
        super.addBindInfo(list, stack);
        if (GuiScreen.isShiftKeyDown()) {
            addStringToTooltip(I18n.format("extrabotany.wings" + stack.getItemDamage()), list);
        }
    }

}
