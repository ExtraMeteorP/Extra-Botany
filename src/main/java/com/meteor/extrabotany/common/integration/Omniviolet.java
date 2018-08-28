package com.meteor.extrabotany.common.integration;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.utils.BaseAction;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.crafting.recipe.RecipeOmniviolet;
import com.meteor.extrabotany.common.lib.LibMisc;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.extrabotany.Omniviolet")
@ModOnly(LibMisc.MOD_ID)
@ZenRegister
public class Omniviolet {
	
	 	@ZenMethod
	    public static void add(int output, IItemStack input) {
	 		ExtraBotany.LATE_ADDITIONS.add(new AddShaped(output, input));
	    }
	    
	    @ZenMethod
	    public static void remove(int output, IItemStack input) {
	    	ExtraBotany.LATE_REMOVALS.add(new RemoveShaped(output, input));
	    }

	    public static class AddShaped extends BaseAction {
	        
	        private final int output;
	        private final IItemStack input;
	        
	        public AddShaped(int output, IItemStack input) {
	            super("Add Stonesia Recipe");
	            this.output = output;
	            this.input = input;
	        }
	        
	        @Override
	        public void apply() {
	            ExtraBotanyAPI.registerOmnivioletRecipe(output, InputHelper.toStack(input));
	        }
	        
	        @Override
	        protected String getRecipeInfo() {
	            return input.getDisplayName();
	        }
	    }
	    
	    public static class RemoveShaped extends BaseAction {
	        
	        private final int output;
	        private final IItemStack input;
	        
	        protected RemoveShaped(int output, IItemStack input) {
	            super("Remove Stonesia Recipe");
	            this.output = output;
	            this.input = input;
	        }
	        
	        @Override
	        public void apply() {
	            if(input != null) {
	            	ExtraBotanyAPI.omnivioletRecipes.remove(new RecipeOmniviolet(output, InputHelper.toStack(input)));
	            }
	        }
	        
	        @Override
	        protected String getRecipeInfo() {
	            return input.getDisplayName();
	        }
	    }

}
