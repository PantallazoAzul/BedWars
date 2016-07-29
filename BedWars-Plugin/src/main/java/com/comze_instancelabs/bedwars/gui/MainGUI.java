/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.comze_instancelabs.bedwars.gui;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import com.comze_instancelabs.bedwars.Main;
import com.comze_instancelabs.minigamesapi.PluginInstance;
import com.comze_instancelabs.minigamesapi.util.IconMenu;
import com.comze_instancelabs.minigamesapi.util.Util;

public class MainGUI {

	Main plugin;
	PluginInstance pli;
	public HashMap<String, IconMenu> lasticonm = new HashMap<String, IconMenu>();
	public LinkedHashMap<String, ItemStack> category_items = new LinkedHashMap<String, ItemStack>();

	public MainGUI(PluginInstance pli, Main plugin) {
		this.plugin = plugin;
		this.pli = pli;
		this.loadCategoryItemsLater();
	}

	public void openGUI(Villager villager, final String p) {
		IconMenu iconm;
		if (lasticonm.containsKey(p)) {
			iconm = lasticonm.get(p);
		} else {
			iconm = new IconMenu(pli.getMessagesConfig().shop_item, 27, new IconMenu.OptionClickEventHandler() {
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent event) {
					if (event.getPlayer().getName().equalsIgnoreCase(p)) {
						if (pli.global_players.containsKey(p)) {
							if (pli.getArenas().contains(pli.global_players.get(p))) {
								String d = event.getName();
								Player p = event.getPlayer();
								openCategory(villager, p, d);
							}
						}
					}
					event.setWillClose(true);
				}
			}, plugin);

			for (int i = 0; i < 9; i++) {
				iconm.setOption(i, new ItemStack(Material.STAINED_GLASS_PANE), "", "");
			}
			for (int i = 18; i < 27; i++) {
				iconm.setOption(i, new ItemStack(Material.STAINED_GLASS_PANE), "", "");
			}
		}

		int c = 9;
		for (String ac : category_items.keySet()) {
			// TODO Category.java with lore and displayname
			iconm.setOption(c, category_items.get(ac), ac, ac);
			c++;
		}

		iconm.open(Bukkit.getPlayerExact(p));
		lasticonm.put(p, iconm);
	}

	public void loadCategoryItemsLater() {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				FileConfiguration config = plugin.gui.getConfig();
				if (config.isSet("maingui.category_items")) {
					for (String aclass : config.getConfigurationSection("maingui.category_items.").getKeys(false)) {
						// TODO Category.java with lore and displayname
						category_items.put(aclass, Util.parseItems(config.getString("maingui.category_items." + aclass + ".items")).get(0));
					}
				}
			}
		}, 20L);
	}

	public void openCategory(Villager villager, final Player p, final String category) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				if (category.equalsIgnoreCase("blocks")) {
					if (plugin.BlocksMerchant.hasCustomer()) {
						plugin.BlocksMerchant.clone().openTrading(villager, p);
					} else {
						plugin.BlocksMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("armor")) {
					if (plugin.ArmorMerchant.hasCustomer()) {
						plugin.ArmorMerchant.clone().openTrading(villager, p);
					} else {
						plugin.ArmorMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("pickaxes")) {
					if (plugin.PickaxesMerchant.hasCustomer()) {
						plugin.PickaxesMerchant.clone().openTrading(villager, p);
					} else {
						plugin.PickaxesMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("swords")) {
					if (plugin.SwordsMerchant.hasCustomer()) {
						plugin.SwordsMerchant.clone().openTrading(villager, p);
					} else {
						plugin.SwordsMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("bows")) {
					if (plugin.BowsMerchant.hasCustomer()) {
						plugin.BowsMerchant.clone().openTrading(villager, p);
					} else {
						plugin.BowsMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("consumables")) {
					if (plugin.ConsumablesMerchant.hasCustomer()) {
						plugin.ConsumablesMerchant.clone().openTrading(villager, p);
					} else {
						plugin.ConsumablesMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("chests")) {
					if (plugin.ChestsMerchant.hasCustomer()) {
						plugin.ChestsMerchant.clone().openTrading(villager, p);
					} else {
						plugin.ChestsMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("potions")) {
					if (plugin.PotionsMerchant.hasCustomer()) {
						plugin.PotionsMerchant.clone().openTrading(villager, p);
					} else {
						plugin.PotionsMerchant.openTrading(villager, p);
					}
				} else if (category.equalsIgnoreCase("specials")) {
					if (plugin.SpecialsMerchant.hasCustomer()) {
						plugin.SpecialsMerchant.clone().openTrading(villager, p);
					} else {
						plugin.SpecialsMerchant.openTrading(villager, p);
					}
				}
			}
		}, 2L);
	}
}
