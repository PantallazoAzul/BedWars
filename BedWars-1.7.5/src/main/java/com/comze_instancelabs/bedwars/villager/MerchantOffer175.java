package com.comze_instancelabs.bedwars.villager;

import org.bukkit.inventory.ItemStack;

public class MerchantOffer175 {
	
	private ItemStack[] items = new ItemStack[3];
	
	public MerchantOffer175(ItemStack is1, ItemStack is2, ItemStack re) {
		this.items[0] = is1;
		this.items[1] = is2;
		this.items[2] = re;
	}
	
	public MerchantOffer175(ItemStack is, ItemStack re) {
		this(is, null, re);
	}
	
	protected MerchantOffer175(NMSMerchantRecipe175 handle) {
		this.items[0] = OBCCraftItemStack175.asBukkitCopy(handle.getBuyItem1());
		this.items[1] = (handle.getBuyItem2() == null) ? null : OBCCraftItemStack175.asBukkitCopy(handle.getBuyItem2());
		this.items[2] = OBCCraftItemStack175.asBukkitCopy(handle.getBuyItem3());
	}
	
	protected NMSMerchantRecipe175 getHandle() {
		if (this.items[1] == null)
			return new NMSMerchantRecipe175(
					(net.minecraft.server.v1_7_R2.ItemStack) OBCCraftItemStack175.asNMSCopy(this.items[0]),
					(net.minecraft.server.v1_7_R2.ItemStack) OBCCraftItemStack175.asNMSCopy(this.items[2]));
		else
			return new NMSMerchantRecipe175(
					(net.minecraft.server.v1_7_R2.ItemStack) OBCCraftItemStack175.asNMSCopy(this.items[0]),
					(net.minecraft.server.v1_7_R2.ItemStack) OBCCraftItemStack175.asNMSCopy(this.items[1]),
					(net.minecraft.server.v1_7_R2.ItemStack) OBCCraftItemStack175.asNMSCopy(this.items[2]));
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
