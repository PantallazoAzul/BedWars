package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_10_R1.MerchantRecipe;
import net.minecraft.server.v1_10_R1.MerchantRecipeList;

public class NMSMerchantRecipeList110 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList110() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList110(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe110 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe110> getRecipes() {
		List<NMSMerchantRecipe110> recipeList = new ArrayList<NMSMerchantRecipe110>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe110((MerchantRecipe) obj));
		}
		return recipeList;
	}
}