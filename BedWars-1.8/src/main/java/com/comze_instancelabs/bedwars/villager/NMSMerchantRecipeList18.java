package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.MerchantRecipe;
import net.minecraft.server.v1_8_R1.MerchantRecipeList;

public class NMSMerchantRecipeList18 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList18() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList18(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe18 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe18> getRecipes() {
		List<NMSMerchantRecipe18> recipeList = new ArrayList<NMSMerchantRecipe18>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe18((MerchantRecipe) obj));
		}
		return recipeList;
	}
}