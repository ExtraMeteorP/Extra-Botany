package com.meteor.extrabotany.common.integration.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.StackHelper;
import com.blamejared.mtlib.utils.BaseAction;
import com.blamejared.mtlib.utils.BaseListRemoval;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.crafting.recipe.RecipeOmniviolet;
import com.meteor.extrabotany.common.lib.LibMisc;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedList;

@ZenClass("mods.extrabotany.Omniviolet")
@ModOnly(LibMisc.MOD_ID)
@ZenRegister
public class Omniviolet {
	
	 	@ZenMethod
	    public static void add(int output, IItemStack input) {
	 		ExtraBotany.LATE_ADDITIONS.add(new AddShaped(output, input));
	    }
	    
	    @ZenMethod
	    public static void remove(IItemStack input) {
	    	ExtraBotany.LATE_REMOVALS.add(new RemoveShaped(input));
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
	    
	    public static class RemoveShaped extends BaseListRemoval<RecipeOmniviolet> {

	        private final IItemStack input;
	        
	        protected RemoveShaped(IItemStack input) {
	            super("Remove Omniviolet Recipe", ExtraBotanyAPI.omnivioletRecipes);
	            this.input = input;
	        }
	        
	        @Override
	        public void apply() {
	        	LinkedList<RecipeOmniviolet> recipes = new LinkedList<>();
	            
	            for(RecipeOmniviolet entry : ExtraBotanyAPI.omnivioletRecipes) {
	                if(entry != null && entry.getInput() != null && StackHelper.matches(input, InputHelper.toIItemStack(entry.getInput()))) {
	                    recipes.add(entry);
	                }
	            }
	            
	            // Check if we found the recipes and apply the action
	            if(!recipes.isEmpty()) {
	                this.recipes.addAll(recipes);
	                super.apply();
	            }
	            CraftTweakerAPI.getLogger().logInfo(super.describe());
	        }
	        
			@Override
			protected String getRecipeInfo(RecipeOmniviolet arg0) {
				return input.getDisplayName();
			}

	    }

}
