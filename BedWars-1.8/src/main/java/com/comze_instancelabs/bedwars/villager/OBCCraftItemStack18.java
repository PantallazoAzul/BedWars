package com.comze_instancelabs.bedwars.villager;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class OBCCraftItemStack18 {

	public static ItemStack asBukkitCopy(Object nmsItemStack) {
		return (ItemStack) CraftItemStack.asBukkitCopy((net.minecraft.server.v1_8_R1.ItemStack) nmsItemStack);
	}

	public static Object asNMSCopy(ItemStack stack) {
		return CraftItemStack.asNMSCopy(stack);
	}

}