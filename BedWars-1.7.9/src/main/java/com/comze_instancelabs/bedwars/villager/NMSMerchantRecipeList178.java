package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R3.MerchantRecipe;
import net.minecraft.server.v1_7_R3.MerchantRecipeList;

public class NMSMerchantRecipeList178 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList178() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList178(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe178 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe178> getRecipes() {
		List<NMSMerchantRecipe178> recipeList = new ArrayList<NMSMerchantRecipe178>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe178((MerchantRecipe) obj));
		}
		return recipeList;
	}
}