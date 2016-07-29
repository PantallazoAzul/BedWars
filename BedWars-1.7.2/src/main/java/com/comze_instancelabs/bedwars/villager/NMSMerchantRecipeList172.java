package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R1.MerchantRecipe;
import net.minecraft.server.v1_7_R1.MerchantRecipeList;

public class NMSMerchantRecipeList172 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList172() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList172(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe172 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe172> getRecipes() {
		List<NMSMerchantRecipe172> recipeList = new ArrayList<NMSMerchantRecipe172>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe172((MerchantRecipe) obj));
		}
		return recipeList;
	}
}