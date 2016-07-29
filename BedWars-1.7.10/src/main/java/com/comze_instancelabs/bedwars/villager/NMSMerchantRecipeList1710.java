package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R4.MerchantRecipe;
import net.minecraft.server.v1_7_R4.MerchantRecipeList;

public class NMSMerchantRecipeList1710 {
	private MerchantRecipeList handle;

	public NMSMerchantRecipeList1710() {
		this.handle = new MerchantRecipeList();
	}

	public NMSMerchantRecipeList1710(MerchantRecipeList handle) {
		this.handle = handle;
	}

	public MerchantRecipeList getHandle() {
		return this.handle;
	}

	public void clear() {
		this.handle.clear();
	}

	public void add(NMSMerchantRecipe1710 recipe) {
		this.handle.add(recipe.getMerchantRecipe());
	}

	public List<NMSMerchantRecipe1710> getRecipes() {
		List<NMSMerchantRecipe1710> recipeList = new ArrayList<NMSMerchantRecipe1710>();
		for (Object obj : handle) {
			recipeList.add(new NMSMerchantRecipe1710((MerchantRecipe) obj));
		}
		return recipeList;
	}
}