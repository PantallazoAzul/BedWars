package com.comze_instancelabs.bedwars.villager;

import org.bukkit.inventory.ItemStack;

public class MerchantOffer19 {

	private ItemStack[] items = new ItemStack[3];

	public MerchantOffer19(ItemStack is1, ItemStack is2, ItemStack re) {
		this.items[0] = is1;
		this.items[1] = is2;
		this.items[2] = re;
	}

	public MerchantOffer19(ItemStack is, ItemStack re) {
		this(is, null, re);
	}

	protected MerchantOffer19(NMSMerchantRecipe19 handle) {
		this.items[0] = OBCCraftItemStack19.asBukkitCopy(handle.getBuyItem1());
		this.items[1] = (handle.getBuyItem2() == null) ? null : OBCCraftItemStack19.asBukkitCopy(handle.getBuyItem2());
		this.items[2] = OBCCraftItemStack19.asBukkitCopy(handle.getBuyItem3());
	}

	protected NMSMerchantRecipe19 getHandle() {
		if (this.items[1] == null)
			return new NMSMerchantRecipe19(
					(net.minecraft.server.v1_9_R1.ItemStack) OBCCraftItemStack19.asNMSCopy(this.items[0]),
					(net.minecraft.server.v1_9_R1.ItemStack) OBCCraftItemStack19.asNMSCopy(this.items[2]));
		else
			return new NMSMerchantRecipe19(
					(net.minecraft.server.v1_9_R1.ItemStack) OBCCraftItemStack19.asNMSCopy(this.items[0]),
					(net.minecraft.server.v1_9_R1.ItemStack) OBCCraftItemStack19.asNMSCopy(this.items[1]),
					(net.minecraft.server.v1_9_R1.ItemStack) OBCCraftItemStack19.asNMSCopy(this.items[2]));
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
