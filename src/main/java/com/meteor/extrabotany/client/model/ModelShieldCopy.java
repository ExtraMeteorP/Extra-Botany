package com.meteor.extrabotany.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShield;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelShieldCopy extends ModelShield{
    public ModelRenderer handle;
    public ModelRenderer boss1;
    public ModelRenderer boss2;
    
    public ModelShieldCopy()
    {
        textureWidth = 64;
        textureHeight = 64;
        
        handle = new ModelRenderer(this, 26, 0);
        handle.addBox(-1F, -3F, -1F, 2, 6, 6);
        
        boss1 = new ModelRenderer(this, 7, 4);
        boss1.addBox(-1.5F, -1.5F, -3.25F, 3, 3, 1);
        
        boss2 = new ModelRenderer(this, 7, 9);
        boss2.addBox(-0.5F, -0.5F, -4.25F, 1, 1, 1);
    }
    
    @Override
    public void render()
    {
        handle.render(0.0625F);
        boss1.render(0.0625F);
        boss2.render(0.0625F);
    }
}
