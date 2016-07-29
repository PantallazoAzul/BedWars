package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R2.MerchantRecipe;
import net.minecraft.server.v1_8_R2.MerchantRecipeList;

public class NMSMerchantRecipeList185 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList185() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList185(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe185 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe185> getRecipes() {
		List<NMSMerchantRecipe185> recipeList = new ArrayList<NMSMerchantRecipe185>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe185((MerchantRecipe) obj));
		}
		return recipeList;
	}
}