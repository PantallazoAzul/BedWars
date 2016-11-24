package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_11_R1.MerchantRecipe;
import net.minecraft.server.v1_11_R1.MerchantRecipeList;

public class NMSMerchantRecipeList111 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList111() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList111(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe111 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe111> getRecipes() {
		List<NMSMerchantRecipe111> recipeList = new ArrayList<NMSMerchantRecipe111>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe111((MerchantRecipe) obj));
		}
		return recipeList;
	}
}