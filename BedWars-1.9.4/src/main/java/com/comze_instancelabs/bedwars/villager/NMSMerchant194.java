package com.comze_instancelabs.bedwars.villager;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.EntityVillager;
import net.minecraft.server.v1_9_R2.MerchantRecipe;
import net.minecraft.server.v1_9_R2.MerchantRecipeList;

public class NMSMerchant194 {
	private NMSMerchantRecipeList194 o = new NMSMerchantRecipeList194();
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
		this.o.add(new NMSMerchantRecipe194(recipe));
	}

	/* Other Methods */
	public Player getBukkitEntity() {
		return this.c.getBukkitEntity();
	}

	public void clearRecipes() {
		this.o.clear();
	}

	public void setRecipes(NMSMerchantRecipeList194 recipes) {
		this.o = recipes;
	}

	public void openTrading(Villager villager, EntityPlayer player, String title) {
		this.c = player;
		final EntityVillager evil = ((CraftVillager) villager).getHandle();
		evil.setTradingPlayer(player);
		evil.getOffers(player).clear();
		evil.getOffers(player).addAll(this.o.getHandle());
		player.openTrade(evil);
	}

}
