package com.comze_instancelabs.bedwars.villager;

import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.MerchantRecipe;

public class NMSMerchantRecipe18 {
	private MerchantRecipe merchantRecipe;

	public NMSMerchantRecipe18(MerchantRecipe merchantRecipe) {
		this.merchantRecipe = merchantRecipe;
	}

	public NMSMerchantRecipe18(ItemStack item1, ItemStack item3) {
		this(item1, null, item3);
	}

	public NMSMerchantRecipe18(ItemStack item1, ItemStack item2, ItemStack item3) {
		this.merchantRecipe = new MerchantRecipe18(item1, item2, item3, 0, 99999999);
	}

	public ItemStack getBuyItem1() {
		return this.merchantRecipe.getBuyItem1();
	}

	public ItemStack getBuyItem2() {
		return this.merchantRecipe.getBuyItem2();
	}

	public ItemStack getBuyItem3() {
		return this.merchantRecipe.getBuyItem3();
	}

	public MerchantRecipe getMerchantRecipe() {
		return this.merchantRecipe;
	}

}