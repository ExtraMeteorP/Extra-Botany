package com.meteor.extrabotany.common.lexicon.page;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconRecipeMappings;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.lexicon.page.PageRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PagePedestalRecipe extends PageRecipe {

    private static final ResourceLocation hammerOverlay = new ResourceLocation("extrabotany:textures/gui/pedestalOverlay.png");

    private final List<RecipePedestal> recipes;
    private final ItemStack renderStack;
    private int ticksElapsed = 0;
    private int recipeAt = 0;

    public PagePedestalRecipe(String unlocalizedName, List<RecipePedestal> recipes) {
        super(unlocalizedName);
        this.recipes = recipes;
        renderStack = new ItemStack(ModBlocks.pedestal);
        ItemNBTHelper.setBoolean(renderStack, "RenderFull", true);
    }

    public PagePedestalRecipe(String unlocalizedName, RecipePedestal recipe) {
        this(unlocalizedName, Collections.singletonList(recipe));
    }

    @Override
    public void onPageAdded(LexiconEntry entry, int index) {
        for (RecipePedestal recipe : recipes)
            LexiconRecipeMappings.map(recipe.getOutput(), entry, index);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my) {
        RecipePedestal recipe = recipes.get(recipeAt);
        TextureManager render = Minecraft.getMinecraft().renderEngine;
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        renderItemAtGridPos(gui, 1, 1, recipe.getInput(), false);
        renderItemAtGridPos(gui, 2, 1, renderStack, false);
        renderItemAtGridPos(gui, 2, 2, new ItemStack(ModItems.hammermanasteel), false);
        renderItemAtGridPos(gui, 3, 1, recipe.getOutput(), false);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int x = gui.getLeft() + gui.getWidth() / 2 - 50;
        int y = gui.getTop() + 115;

        String dropString = I18n.format("extrabotanymisc.hammer") + " " + TextFormatting.BOLD + "(?)";
        boolean hoveringOverDrop = false;
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);
        int dw = font.getStringWidth(dropString);
        int dx = x + 35 - dw / 2;
        int dy = gui.getTop() + 30;

        if (mx > dx && mx <= dx + dw && my > dy && my <= dy + 10)
            hoveringOverDrop = true;

        font.drawString(dropString, dx, dy, 0x77000000);
        font.setUnicodeFlag(unicode);

        GlStateManager.disableBlend();

        render.bindTexture(hammerOverlay);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
        GlStateManager.disableBlend();

        if (hoveringOverDrop) {
            String tip0 = I18n.format("extrabotanymisc.hammerTip");
            RenderHelper.renderTooltip(mx, my, Arrays.asList(tip0));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateScreen() {
        if (GuiScreen.isShiftKeyDown())
            return;

        if (ticksElapsed % 20 == 0) {
            recipeAt++;

            if (recipeAt == recipes.size())
                recipeAt = 0;
        }
        ++ticksElapsed;
    }

    @Override
    public List<ItemStack> getDisplayedRecipes() {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (RecipePedestal r : recipes)
            list.add(r.getOutput());

        return list;
    }
}
