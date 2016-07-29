package com.comze_instancelabs.bedwars.villager;

import org.bukkit.craftbukkit.v1_7_R2.entity.CraftVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.EntityPlayer;
import net.minecraft.server.v1_7_R2.EntityVillager;
import net.minecraft.server.v1_7_R2.MerchantRecipe;
import net.minecraft.server.v1_7_R2.MerchantRecipeList;

public class NMSMerchant175 {
	private NMSMerchantRecipeList175 o = new NMSMerchantRecipeList175();
	private transient EntityPlayer c;

	public void setCustomer(EntityPlayer player) {
		this.c = player;
	}

	public EntityPlayer getCustomer() {
		return this.c;
	}

	public MerchantRecipeList getOffers(EntityHuman player) {
		return this.o.getHandle();
	}

	public void addOffer(MerchantRecipe recipe) {
		this.o.add(new NMSMerchantRecipe175(recipe));
	}

	/* Other Methods */
	public Player getBukkitEntity() {
		return this.c.getBukkitEntity();
	}

	public void clearRecipes() {
		this.o.clear();
	}

	public void setRecipes(NMSMerchantRecipeList175 recipes) {
		this.o = recipes;
	}

	public void openTrading(Villager villager, EntityPlayer player, String title) {
		this.c = player;
		final EntityVillager evil = ((CraftVillager)villager).getHandle();
		evil.a_(player);
		evil.getOffers(player).clear();
		evil.getOffers(player).addAll(this.o.getHandle());
		player.openTrade(evil, title);
		// 1.8 and newer: remove string argument
	}

}
