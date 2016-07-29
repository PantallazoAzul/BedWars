package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_9_R1.MerchantRecipe;
import net.minecraft.server.v1_9_R1.MerchantRecipeList;

public class NMSMerchantRecipeList19 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList19() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList19(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe19 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe19> getRecipes() {
		List<NMSMerchantRecipe19> recipeList = new ArrayList<NMSMerchantRecipe19>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe19((MerchantRecipe) obj));
		}
		return recipeList;
	}
}