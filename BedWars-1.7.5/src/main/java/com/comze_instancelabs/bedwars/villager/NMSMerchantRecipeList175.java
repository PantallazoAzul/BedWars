package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R2.MerchantRecipe;
import net.minecraft.server.v1_7_R2.MerchantRecipeList;

public class NMSMerchantRecipeList175 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList175() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList175(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe175 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe175> getRecipes() {
		List<NMSMerchantRecipe175> recipeList = new ArrayList<NMSMerchantRecipe175>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe175((MerchantRecipe) obj));
		}
		return recipeList;
	}
}