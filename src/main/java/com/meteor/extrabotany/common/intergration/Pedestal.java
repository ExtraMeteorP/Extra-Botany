package com.meteor.extrabotany.common.intergration;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.utils.BaseAction;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.lib.LibMisc;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.extrabotany.Pedestal")
@ModOnly(LibMisc.MOD_ID)
@ZenRegister
public class Pedestal {
	
	 	@ZenMethod
	    public static void add(IItemStack output, IItemStack input) {
	 		ExtraBotany.LATE_ADDITIONS.add(new AddShaped(output, input));
	    }
	    
	    @ZenMethod
	    public static void remove(IItemStack output, @Optional IItemStack input) {
	    	ExtraBotany.LATE_REMOVALS.add(new RemoveShaped(output, input));
	    }

	    public static class AddShaped extends BaseAction {
	        
	        private final IItemStack output;
	        private final IItemStack input;
	        
	        public AddShaped(IItemStack output, IItemStack input) {
	            super("Add Pedestal Recipe");
	            this.output = output;
	            this.input = input;
	        }
	        
	        @Override
	        public void apply() {
	            ExtraBotanyAPI.registerPedestalRecipe(InputHelper.toStack(output), InputHelper.toStack(input));
	        }
	        
	        @Override
	        protected String getRecipeInfo() {
	            return output.getDisplayName();
	        }
	    }
	    
	    public static class RemoveShaped extends BaseAction {
	        
	        private final IItemStack output;
	        private final IItemStack input;
	        
	        protected RemoveShaped(IItemStack output, IItemStack input) {
	            super("Remove Pedestal Recipe");
	            this.output = output;
	            this.input = input;
	        }
	        
	        @Override
	        public void apply() {
	            if(input != null) {
	            	ExtraBotanyAPI.pedestalRecipes.remove(new RecipePedestal(InputHelper.toStack(output), InputHelper.toStack(input)));
	            }
	        }
	        
	        @Override
	        protected String getRecipeInfo() {
	            return output.getDisplayName();
	        }
	    }

}
