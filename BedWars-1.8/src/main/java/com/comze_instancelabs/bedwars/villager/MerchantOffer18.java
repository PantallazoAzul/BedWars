package com.comze_instancelabs.bedwars.villager;

import org.bukkit.inventory.ItemStack;

public class MerchantOffer18 {

	private ItemStack[] items = new ItemStack[3];

	public MerchantOffer18(ItemStack is1, ItemStack is2, ItemStack re) {
		this.items[0] = is1;
		this.items[1] = is2;
		this.items[2] = re;
	}

	public MerchantOffer18(ItemStack is, ItemStack re) {
		this(is, null, re);
	}

	protected MerchantOffer18(NMSMerchantRecipe18 handle) {
		this.items[0] = OBCCraftItemStack18.asBukkitCopy(handle.getBuyItem1());
		this.items[1] = (handle.getBuyItem2() == null) ? null : OBCCraftItemStack18.asBukkitCopy(handle.getBuyItem2());
		this.items[2] = OBCCraftItemStack18.asBukkitCopy(handle.getBuyItem3());
	}

	protected NMSMerchantRecipe18 getHandle() {
		if (this.items[1] == null)
			return new NMSMerchantRecipe18(
					(net.minecraft.server.v1_8_R1.ItemStack) OBCCraftItemStack18.asNMSCopy(this.items[0]),
					(net.minecraft.server.v1_8_R1.ItemStack) OBCCraftItemStack18.asNMSCopy(this.items[2]));
		else
			return new NMSMerchantRecipe18(
					(net.minecraft.server.v1_8_R1.ItemStack) OBCCraftItemStack18.asNMSCopy(this.items[0]),
					(net.minecraft.server.v1_8_R1.ItemStack) OBCCraftItemStack18.asNMSCopy(this.items[1]),
					(net.minecraft.server.v1_8_R1.ItemStack) OBCCraftItemStack18.asNMSCopy(this.items[2]));
	}

	public ItemStack getFirstInput() {
		return this.items[0];
	}

	public ItemStack getSecondInput() {
		return this.items[1];
	}

	public ItemStack getOutput() {
		return this.items[2];
	}

}
