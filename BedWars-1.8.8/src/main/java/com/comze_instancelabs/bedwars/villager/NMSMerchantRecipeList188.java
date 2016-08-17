package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.MerchantRecipe;
import net.minecraft.server.v1_8_R3.MerchantRecipeList;

public class NMSMerchantRecipeList188 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList188() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList188(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe188 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe188> getRecipes() {
		List<NMSMerchantRecipe188> recipeList = new ArrayList<NMSMerchantRecipe188>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe188((MerchantRecipe) obj));
		}
		return recipeList;
	}
}