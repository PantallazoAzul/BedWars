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
package com.comze_instancelabs.bedwars;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.comze_instancelabs.bedwars.gui.MainGUI;
import com.comze_instancelabs.bedwars.gui.TeamSelectorGUI;
import com.comze_instancelabs.bedwars.sheep.Register;
import com.comze_instancelabs.bedwars.sheep.Register110;
import com.comze_instancelabs.bedwars.sheep.Register111;
import com.comze_instancelabs.bedwars.sheep.Register18;
import com.comze_instancelabs.bedwars.sheep.Register185;
import com.comze_instancelabs.bedwars.sheep.Register188;
import com.comze_instancelabs.bedwars.sheep.Register19;
import com.comze_instancelabs.bedwars.sheep.Register194;
import com.comze_instancelabs.bedwars.sheep.Sheeep;
import com.comze_instancelabs.bedwars.villager.Merchant;
import com.comze_instancelabs.bedwars.villager.Merchant110;
import com.comze_instancelabs.bedwars.villager.Merchant111;
import com.comze_instancelabs.bedwars.villager.Merchant18;
import com.comze_instancelabs.bedwars.villager.Merchant185;
import com.comze_instancelabs.bedwars.villager.Merchant188;
import com.comze_instancelabs.bedwars.villager.Merchant19;
import com.comze_instancelabs.bedwars.villager.Merchant194;
import com.comze_instancelabs.minigamesapi.Arena;
import com.comze_instancelabs.minigamesapi.ArenaConfigStrings;
import com.comze_instancelabs.minigamesapi.ArenaSetup;
import com.comze_instancelabs.minigamesapi.ArenaState;
import com.comze_instancelabs.minigamesapi.ArenaType;
import com.comze_instancelabs.minigamesapi.MinigamesAPI;
import com.comze_instancelabs.minigamesapi.PluginInstance;
import com.comze_instancelabs.minigamesapi.config.ArenasConfig;
import com.comze_instancelabs.minigamesapi.config.DefaultConfig;
import com.comze_instancelabs.minigamesapi.config.MessagesConfig;
import com.comze_instancelabs.minigamesapi.config.StatsConfig;
import com.comze_instancelabs.minigamesapi.util.Cuboid;
import com.comze_instancelabs.minigamesapi.util.Util;
import com.comze_instancelabs.minigamesapi.util.Validator;

public class Main extends JavaPlugin implements Listener {

	// TODO
	// Add tnt-sheep and gravi-bomb
	// Bugs:
	// - when doing reload items don't get cleared

	Register reg;

	MinigamesAPI api = null;
	PluginInstance pli = null;
	static Main m = null;
	public IArenaScoreboard scoreboard = new IArenaScoreboard(this);
	ICommandHandler cmdhandler = new ICommandHandler();

	public MainGUI maingui;
	public TeamSelectorGUI teamgui;
	public GUIConfig gui;

	// Player -> Team
	public static HashMap<String, String> pteam = new HashMap<String, String>();

	public Merchant BlocksMerchant;
	public Merchant ArmorMerchant;
	public Merchant PickaxesMerchant;
	public Merchant SwordsMerchant;
	public Merchant BowsMerchant;
	public Merchant ConsumablesMerchant;
	public Merchant ChestsMerchant;
	public Merchant PotionsMerchant;
	public Merchant SpecialsMerchant;

	public void onEnable() {
		m = this;
		api = MinigamesAPI.getAPI().setupAPI(this, "bedwars", IArena.class, new ArenasConfig(this),
				new MessagesConfig(this), new IClassesConfig(this), new StatsConfig(this, false),
				new DefaultConfig(this, false), true);
		PluginInstance pinstance = api.pinstances.get(this);
		pinstance.addLoadedArenas(loadArenas(this, pinstance.getArenasConfig()));
		Bukkit.getPluginManager().registerEvents(this, this);
		pinstance.scoreboardManager = new IArenaScoreboard(this);
		pinstance.arenaSetup = new IArenaSetup();
		IArenaListener listener = new IArenaListener(this, pinstance, "bedwars");
		pinstance.setArenaListener(listener);
		MinigamesAPI.getAPI().registerArenaListenerLater(this, listener);
		pinstance.setAchievementGuiEnabled(true);
		pli = pinstance;

		gui = new GUIConfig(this);
		maingui = new MainGUI(pli, this);
		teamgui = new TeamSelectorGUI(pli, this);

		boolean continue_ = false;
		for (Method m : pli.getArenaAchievements().getClass().getMethods()) {
			if (m.getName().equalsIgnoreCase("addDefaultAchievement")) {
				continue_ = true;
			}
		}
		if (continue_) {
			// pli.getArenaAchievements().addDefaultAchievement("destroy_hundred_blocks_with_bow",
			// "Destroy 100 blocks with your bow in one game!",
			// 100);
			// pli.getArenaAchievements().addDefaultAchievement("destroy_thousand_blocks_with_bow_alltime",
			// "Destroy 1000 blocks with your bow all-time!", 1000);
			// pli.getArenaAchievements().addDefaultAchievement("win_game_with_one_life",
			// "Win a game with one life left!", 200);
			// pli.getAchievementsConfig().getConfig().options().copyDefaults(true);
			// pli.getAchievementsConfig().saveConfig();
		}

		// load villager trades
		BlocksMerchant = createMerchant("Blocks");
		ArmorMerchant = createMerchant("Armor");
		PickaxesMerchant = createMerchant("Pickaxes");
		SwordsMerchant = createMerchant("Swords");
		BowsMerchant = createMerchant("Bows");
		ConsumablesMerchant = createMerchant("Consumables");
		ChestsMerchant = createMerchant("Chests");
		PotionsMerchant = createMerchant("Potions");
		SpecialsMerchant = createMerchant("Specials");

		FileConfiguration config = gui.getConfig();

		loadTrades(config, "blocksgui.trades.", BlocksMerchant);
		loadTrades(config, "armorgui.trades.", ArmorMerchant);
		loadTrades(config, "pickaxesgui.trades.", PickaxesMerchant);
		loadTrades(config, "swordsgui.trades.", SwordsMerchant);
		loadTrades(config, "bowsgui.trades.", BowsMerchant);
		loadTrades(config, "consumablesgui.trades.", ConsumablesMerchant);
		loadTrades(config, "chestsgui.trades.", ChestsMerchant);
		loadTrades(config, "potionsgui.trades.", PotionsMerchant);
		loadTrades(config, "specialsgui.trades.", SpecialsMerchant);

		// Bed message
		pli.getMessagesConfig().getConfig().addDefault("messages.bed_destroyed",
				"&cTeam &4<team>&c's bed was destroyed!");
		pli.getMessagesConfig().getConfig().options().copyDefaults(true);
		pli.getMessagesConfig().saveConfig();

		switch (MinigamesAPI.SERVER_VERSION) {
		case Unknown:
		default:
			break;
		case V1_10:
		case V1_10_R1:
			reg = new Register110();
			break;
		case V1_11:
		case V1_11_R1:
			reg = new Register111();
			break;
		case V1_8:
		case V1_8_R1:
			reg = new Register18();
			break;
		case V1_8_R2:
			reg = new Register185();
			break;
		case V1_8_R3:
			reg = new Register188();
			break;
		case V1_9:
		case V1_9_R1:
			reg = new Register19();
			break;
		case V1_9_R2:
			reg = new Register194();
			break;
		}
		reg.registerEntities();
	}

	private Merchant createMerchant(String string) {
		switch (MinigamesAPI.SERVER_VERSION) {
		case Unknown:
		default:
			break;
		case V1_11:
		case V1_11_R1:
			return new Merchant111(string);
		case V1_10:
		case V1_10_R1:
			return new Merchant110(string);
		case V1_8:
		case V1_8_R1:
			return new Merchant18(string);
		case V1_8_R2:
			return new Merchant185(string);
		case V1_8_R3:
			return new Merchant188(string);
		case V1_9:
		case V1_9_R1:
			return new Merchant19(string);
		case V1_9_R2:
			return new Merchant194(string);
		}
		return null;
	}

	public void loadTrades(FileConfiguration config, String path, Merchant m) {
		if (config.isSet(path)) {
			for (String aclass : config.getConfigurationSection(path).getKeys(false)) {
				ArrayList<ItemStack> items = Util.parseItems(config.getString(path + aclass + ".items"));
				if (items.size() > 2) {
					m.addOffer(items.get(1), items.get(2), items.get(0));
				} else {
					m.addOffer(items.get(1), items.get(0));
				}
			}
		}
	}

	public static ArrayList<Arena> loadArenas(JavaPlugin plugin, ArenasConfig cf) {
		ArrayList<Arena> ret = new ArrayList<Arena>();
		FileConfiguration config = cf.getConfig();
		if (!config.isSet("arenas")) {
			return ret;
		}
		for (String arena : config.getConfigurationSection(ArenaConfigStrings.ARENAS_PREFIX).getKeys(false)) {
			if (Validator.isArenaValid(plugin, arena, cf.getConfig())) {
				ret.add(initArena(arena));
			}
		}
		return ret;
	}

	public static IArena initArena(String arena) {
		IArena a = new IArena(m, arena);
		ArenaSetup s = MinigamesAPI.getAPI().pinstances.get(m).arenaSetup;
		a.init(Util.getSignLocationFromArena(m, arena), Util.getAllSpawns(m, arena), Util.getMainLobby(m),
				Util.getComponentForArena(m, arena, "lobby"), s.getPlayerCount(m, arena, true),
				s.getPlayerCount(m, arena, false), s.getArenaVIP(m, arena));
		return a;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean ret = cmdhandler.handleArgs(this, MinigamesAPI.getAPI().getPermissionGamePrefix("bedwars"),
				"/" + cmd.getName(), sender, args);

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("setupbeds")) {
				if (args.length > 1) {
					// /bw setupbeds <arena>
					if (sender instanceof Player) {
						Player p = (Player) sender;

						p.getInventory().addItem(getSetupBed(args[1], "red"));
						p.getInventory().addItem(getSetupBed(args[1], "blue"));
						p.getInventory().addItem(getSetupBed(args[1], "green"));
						p.getInventory().addItem(getSetupBed(args[1], "yellow"));

						p.updateInventory();
					} else {
						sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "]"
								+ ChatColor.GRAY + " Usage: " + cmd.getName() + " setupbeds <arena> <team>");
					}
				}
			}
		}
		return ret;
	}

	public ItemStack getSetupBed(String arena, String team) {
		ItemStack item = new ItemStack(Material.BED);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("bwbeds:" + team + "#" + arena);
		item.setItemMeta(itemmeta);
		return item;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.BED_BLOCK) {
			if (event.getItemInHand().hasItemMeta()) {
				if (event.getItemInHand().getItemMeta().hasDisplayName()) {
					String displayname = event.getItemInHand().getItemMeta().getDisplayName();
					if (displayname.startsWith("bwbeds:")) {
						// bwbed:team#arena
						int a = displayname.indexOf("#");
						String arena = displayname.substring(a + 1);
						String team = displayname.substring(displayname.indexOf(":") + 1, a);
						this.getLogger().fine("#" + arena + " " + team);
						Util.saveComponentForArena(this, arena, team + "_bed.loc1", event.getBlock().getLocation());
						Location l = event.getBlock().getLocation();
						for (int i = -3; i < 3; i++) {
							for (int j = -3; j < 3; j++) {
								Location l_ = l.clone().add(i, 0, j);
								if (l.getBlockX() == l_.getBlockX() && l.getBlockY() == l_.getBlockY()
										&& l.getBlockZ() == l_.getBlockZ()) {
									// Skip
								} else {
									if (l_.getBlock().getType() == Material.BED_BLOCK) {
										Util.saveComponentForArena(this, arena, team + "_bed.loc2", l_);
									}
								}
							}
						}
					}
				}
			}
		} else if (event.getBlock().getType() == Material.WOOL) {
			if (pli.global_players.containsKey(event.getPlayer().getName())) {
				event.setCancelled(true);
			}
		} else {
			if (pli.global_players.containsKey(event.getPlayer().getName())) {
				final Arena a = this.pli.global_players.get(event.getPlayer().getName());
				if (a.getArenaState() == ArenaState.INGAME) {
					a.getSmartReset().addChanged(event.getBlock(), event.getBlockReplacedState());
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerBedEnterEvent event) {
		if (pli.global_players.containsKey(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		final Player p = event.getPlayer();
		if (pli.global_players.containsKey(p.getName())) {
			if (event.getRightClicked().getType() == EntityType.VILLAGER) {
				Bukkit.getScheduler().runTaskLater(this, new Runnable() {
					public void run() {
						maingui.openGUI((Villager) event.getRightClicked(), p.getName());
					}
				}, 1L);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		if (pli.global_players.containsKey(p.getName())) {
			if (event.getInventory().getName().equalsIgnoreCase("container.crafting")) {
				if (event.getSlotType() == SlotType.RESULT) {
					event.setCancelled(true);
				}
			}
		}

	}

	@EventHandler
	public void onInteract(final PlayerInteractEvent event) {
		if (event.hasItem()) {
			if (pli.global_players.containsKey(event.getPlayer().getName())) {
				Arena a = pli.global_players.get(event.getPlayer().getName());
				if (event.getItem().getType() == Material.WOOL) {
					if (a.getArenaState() != ArenaState.INGAME && !a.isArcadeMain() && !a.getIngameCountdownStarted()) {
						teamgui.openGUI(event.getPlayer().getName());
					}
				} else if (event.getItem().getType() == Material.MONSTER_EGG) {
					if (event.hasBlock()) {
						if (pteam.containsKey(event.getPlayer().getName())) {
							String team = pteam.get(event.getPlayer().getName());
							Location l = event.getPlayer().getLocation();
							Player target = null;
							int temp_dist = 0;
							for (String p_ : a.getAllPlayers()) {
								if (Validator.isPlayerOnline(p_)) {
									Player p = Bukkit.getPlayer(p_);
									if (pteam.containsKey(p.getName()) && pli.global_players.containsKey(p.getName())) {
										if (!pteam.get(p.getName()).equalsIgnoreCase(team)) {
											int dist = (int) p.getLocation().distance(l);
											if (dist >= temp_dist) {
												temp_dist = dist;
												target = p;
											}
											break;
										}
									}
								}
							}
							if (target != null) {
								this.getLogger().fine("# Spawned sheeep for target " + target.getName());
								final Sheeep s = reg.spawnSheep(this,
										event.getClickedBlock().getLocation().add(0D, 1D, 0D), target,
										colorCodeFromTeam(team));
								final ItemStack item = event.getPlayer().getItemInHand();
								l.getBlock().getChunk().load();
								Bukkit.getScheduler().runTaskLater(this, new Runnable() {
									public void run() {
										event.getPlayer().getInventory().remove(item);
										event.getPlayer().updateInventory();
									}
								}, 5L);
								Bukkit.getScheduler().runTaskLater(this, new Runnable() {
									public void run() {
										Location l = s.getLocation();
										l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 2.5F, true, false);
									}
								}, 20L * 7);
							}
						}
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent event) {
		final Player p = event.getPlayer();
		if (pli.global_players.containsKey(p.getName())) {
			IArena a = (IArena) pli.global_players.get(p.getName());
			if (a.getArenaState() == ArenaState.INGAME) {
				if (p.getLocation().getY() < 0) {
					// player fell
					if (pteam.containsKey(p.getName())) {
						String team = m.pteam.get(p.getName());
						String playername = p.getName();
						Util.clearInv(p);
						if (team.equalsIgnoreCase("red")) {
							if (a.red_bed) {
								a.onEliminated(playername);
								Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
										"spawns.spawn" + m.pteam.get(playername)));
							} else {
								a.spectate(p.getName(), true);
							}
						} else if (team.equalsIgnoreCase("blue")) {
							if (a.blue_bed) {
								a.onEliminated(playername);
								Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
										"spawns.spawn" + m.pteam.get(playername)));
							} else {
								a.spectate(p.getName(), true);
							}
						} else if (team.equalsIgnoreCase("green")) {
							if (a.green_bed) {
								a.onEliminated(playername);
								Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
										"spawns.spawn" + m.pteam.get(playername)));
							} else {
								a.spectate(p.getName(), true);
							}
						} else if (team.equalsIgnoreCase("yellow")) {
							if (a.yellow_bed) {
								a.onEliminated(playername);
								Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
										"spawns.spawn" + m.pteam.get(playername)));
							} else {
								a.spectate(p.getName(), true);
							}
						}
						scoreboard.updateScoreboard(a);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		if (pli.global_players.containsKey(p.getName())) {
			IArena a = (IArena) pli.global_players.get(p.getName());
			if (a.getArenaState() == ArenaState.INGAME) {
				event.setDeathMessage(null);
				p.setHealth(20D);
				event.getDrops().clear();
				if (m.pteam.containsKey(p.getName())) {
					String team = m.pteam.get(p.getName());
					String playername = p.getName();
					if (team.equalsIgnoreCase("red")) {
						if (a.red_bed) {
							a.onEliminated(playername);
							Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
									"spawns.spawn" + m.pteam.get(playername)));
						} else {
							a.spectate(p.getName(), true);
							a.red--;
						}
					} else if (team.equalsIgnoreCase("blue")) {
						if (a.blue_bed) {
							a.onEliminated(playername);
							Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
									"spawns.spawn" + m.pteam.get(playername)));
						} else {
							a.spectate(p.getName(), true);
							a.blue--;
						}
					} else if (team.equalsIgnoreCase("green")) {
						if (a.green_bed) {
							a.onEliminated(playername);
							Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
									"spawns.spawn" + m.pteam.get(playername)));
						} else {
							a.spectate(p.getName(), true);
							a.green--;
						}
					} else if (team.equalsIgnoreCase("yellow")) {
						if (a.yellow_bed) {
							a.onEliminated(playername);
							Util.teleportPlayerFixed(p, Util.getComponentForArena(m, a.getName(),
									"spawns.spawn" + m.pteam.get(playername)));
						} else {
							a.spectate(p.getName(), true);
							a.yellow--;
						}
					}
					teamCheck(a);
				}
			}
		}
	}

	public void teamCheck(IArena a) {
		// If only one team left, stop
		if (a.blue < 1 && a.red < 1 && a.yellow < 1 && a.green > 0) {
			a.stop();
		}
		if (a.blue < 1 && a.red < 1 && a.yellow > 0 && a.green < 1) {
			a.stop();
		}
		if (a.blue < 1 && a.red > 0 && a.yellow < 1 && a.green < 1) {
			a.stop();
		}
		if (a.blue > 0 && a.red < 1 && a.yellow < 1 && a.green < 1) {
			a.stop();
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			final Player p = (Player) event.getEntity();
			if (pli.global_players.containsKey(p.getName())) {
				IArena a = (IArena) pli.global_players.get(p.getName());
				if (a.getArenaState() == ArenaState.INGAME) {
					if (event.getDamager() instanceof Player) {
						Player p2 = (Player) event.getDamager();
						if (m.pteam.get(p.getName()).equalsIgnoreCase(m.pteam.get(p2.getName()))) {
							// same team
							event.setCancelled(true);
						}
					} else if (event.getDamager() instanceof Arrow) {
						Arrow ar = (Arrow) event.getDamager();
						if (ar.getShooter() instanceof Player) {
							Player p2 = (Player) ar.getShooter();
							if (m.pteam.get(p.getName()).equalsIgnoreCase(m.pteam.get(p2.getName()))) {
								// same team
								event.setCancelled(true);
							}
						}
					}
				}
			}
		} else {
			if (event.getDamager() instanceof Player) {
				Player p = (Player) event.getDamager();
				if (pli.global_players.containsKey(p.getName())) {
					event.setCancelled(true);
				}
			} else if (event.getDamager() instanceof Arrow) {
				Arrow ar = (Arrow) event.getDamager();
				if (ar.getShooter() instanceof Player) {
					Player p = (Player) ar.getShooter();
					if (pli.global_players.containsKey(p.getName())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		for (Arena a : pli.getArenas()) {
			if (Validator.isArenaValid(this, a) && a.getArenaType() == ArenaType.REGENERATION) {
				Cuboid c = a.getBoundaries();
				if (c != null && a.getArenaState() == ArenaState.INGAME) {
					if (c.containsLocWithoutY(event.getEntity().getLocation())) {
						if (event.getEntity().getItemStack().getType() == Material.BED) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		final Player p = event.getPlayer();
		if (pli.global_players.containsKey(p.getName())) {
			IArena a = (IArena) pli.global_players.get(p.getName());
			if (a.getArenaState() == ArenaState.INGAME) {
				if (event.getBlock().getType() == Material.BED_BLOCK) {
					String team = getTeambyBedLocation(a.getName(), event.getBlock().getLocation());
					if (team == "-") {
						event.setCancelled(true);
						return;
					}
					if (m.pteam.get(p.getName()).equalsIgnoreCase(team)) {
						// don't allow destroying own bed
						event.setCancelled(true);
						return;
					}
					if (team.equalsIgnoreCase("red")) {
						a.red_bed = false;
					} else if (team.equalsIgnoreCase("green")) {
						a.green_bed = false;
					} else if (team.equalsIgnoreCase("blue")) {
						a.blue_bed = false;
					} else if (team.equalsIgnoreCase("yellow")) {
						a.yellow_bed = false;
					} else {
						return;
					}
					a.getSmartReset().addChanged(event.getBlock(), event.getBlock().getType().equals(Material.CHEST));
					a.getSmartReset().addChanged(event.getBlock().getLocation().clone().add(1D, 0D, 0D).getBlock(),
							event.getBlock().getLocation().clone().add(1D, 0D, 1D).getBlock().getType()
									.equals(Material.CHEST));
					a.getSmartReset().addChanged(event.getBlock().getLocation().clone().add(-1D, 0D, 0D).getBlock(),
							event.getBlock().getLocation().clone().add(1D, 0D, -1D).getBlock().getType()
									.equals(Material.CHEST));
					a.getSmartReset().addChanged(event.getBlock().getLocation().clone().add(0D, 0D, 1D).getBlock(),
							event.getBlock().getLocation().clone().add(-1D, 0D, 1D).getBlock().getType()
									.equals(Material.CHEST));
					a.getSmartReset().addChanged(event.getBlock().getLocation().clone().add(0D, 0D, -1D).getBlock(),
							event.getBlock().getLocation().clone().add(-1D, 0D, -1D).getBlock().getType()
									.equals(Material.CHEST));
					event.getBlock().setType(Material.AIR);
					for (String p_ : a.getAllPlayers()) {
						if (Validator.isPlayerOnline(p_)) {
							Bukkit.getPlayer(p_)
									.sendMessage(ChatColor.translateAlternateColorCodes('&', pli.getMessagesConfig()
											.getConfig().getString("messages.bed_destroyed").replaceAll("<team>",
													Character.toUpperCase(team.charAt(0)) + team.substring(1))));
						}
					}
					return;
				}
				if (event.getBlock().getType() != Material.IRON_BLOCK
						&& event.getBlock().getType() != Material.ENDER_CHEST
						&& event.getBlock().getType() != Material.TNT
						&& event.getBlock().getType() != Material.SANDSTONE
						&& event.getBlock().getType() != Material.GLOWSTONE
						&& event.getBlock().getType() != Material.ENDER_STONE
						&& event.getBlock().getType() != Material.GLASS && event.getBlock().getType() != Material.LADDER
						&& event.getBlock().getType() != Material.CHEST) {
					event.setCancelled(true);
				}
			}
		}
	}

	public String getTeambyBedLocation(String arena, Location l) {
		String ret = "-";
		HashMap<String, Location> temp = new HashMap<String, Location>();
		temp.put("yellow_1", Util.getComponentForArena(this, arena, "yellow_bed.loc1"));
		temp.put("yellow_2", Util.getComponentForArena(this, arena, "yellow_bed.loc2"));
		temp.put("red_1", Util.getComponentForArena(this, arena, "red_bed.loc1"));
		temp.put("red_2", Util.getComponentForArena(this, arena, "red_bed.loc2"));
		temp.put("blue_1", Util.getComponentForArena(this, arena, "blue_bed.loc1"));
		temp.put("blue_2", Util.getComponentForArena(this, arena, "blue_bed.loc2"));
		temp.put("green_1", Util.getComponentForArena(this, arena, "green_bed.loc1"));
		temp.put("green_2", Util.getComponentForArena(this, arena, "green_bed.loc2"));

		for (String team : temp.keySet()) {
			if (temp.get(team) != null) {
				Location l_ = temp.get(team);
				if (l.getBlockX() == l_.getBlockX() && l.getBlockY() == l_.getBlockY()
						&& l.getBlockZ() == l_.getBlockZ()) {
					return team.substring(0, team.indexOf("_"));
				}
			}
		}

		return ret;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (pli.global_players.containsKey(p.getName())) {
			if (!m.pteam.containsKey(p.getName())) {
				return;
			}
			if (event.getMessage().startsWith("@")) {
				return;
			} else if (event.getMessage().startsWith("all")) {
				event.setMessage(event.getMessage().replace("all", ""));
				return;
			}
			String team = m.pteam.get(p.getName());
			String msg = String.format(ChatColor.GRAY + "[" + ChatColor.valueOf(team.toUpperCase()) + team
					+ ChatColor.GRAY + "] " + event.getFormat(), p.getName(), event.getMessage());
			for (Player receiver : event.getRecipients()) {
				if (pli.global_players.containsKey(receiver.getName())) {
					if (pli.global_players.get(receiver.getName()) == pli.global_players.get(p.getName())) {
						if (m.pteam.containsKey(receiver.getName())) {
							if (m.pteam.get(receiver.getName()).equalsIgnoreCase(team)) {
								receiver.sendMessage(msg);
							}
						}
					}
				}
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		final Player p = event.getPlayer();
		if (pli.global_players.containsKey(p.getName())) {
			for (Entity e : p.getLocation().getChunk().getEntities()) {
				if (e.getType() == EntityType.VILLAGER) {
					Villager v = (Villager) e;
					v.setVelocity(new Vector(0F, 0.1F, 0F));
				}
			}
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				public void run() {
					p.getLocation().getChunk().unload(true, false);
					p.getLocation().getChunk().load();
				}
			}, 10L);
		}
	}

	public int colorCodeFromTeam(String team) {
		int ret = 0;
		if (team.equalsIgnoreCase("red")) {
			ret = 14;
		} else if (team.equalsIgnoreCase("blue")) {
			ret = 11;
		} else if (team.equalsIgnoreCase("yellow")) {
			ret = 4;
		} else if (team.equalsIgnoreCase("green")) {
			ret = 13;
		}
		return ret;
	}

}
