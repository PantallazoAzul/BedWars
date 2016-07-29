package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_9_R2.MerchantRecipe;
import net.minecraft.server.v1_9_R2.MerchantRecipeList;

public class NMSMerchantRecipeList194 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList194() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList194(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe194 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe194> getRecipes() {
		List<NMSMerchantRecipe194> recipeList = new ArrayList<NMSMerchantRecipe194>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe194((MerchantRecipe) obj));
		}
		return recipeList;
	}
}