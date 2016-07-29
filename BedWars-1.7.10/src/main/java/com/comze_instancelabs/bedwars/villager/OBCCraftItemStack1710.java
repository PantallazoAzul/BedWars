package com.comze_instancelabs.bedwars.villager;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class OBCCraftItemStack1710 {

	public static ItemStack asBukkitCopy(Object nmsItemStack) {
		return (ItemStack) CraftItemStack.asBukkitCopy((net.minecraft.server.v1_7_R4.ItemStack) nmsItemStack);
	}

	public static Object asNMSCopy(ItemStack stack) {
		return CraftItemStack.asNMSCopy(stack);
	}

}