package com.comze_instancelabs.bedwars.sheep;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_8_R2.EntityTypes;
import net.minecraft.server.v1_8_R2.EnumColor;

public class Register185 extends Register {

	public boolean registerEntities() {
		try {
			Class entityTypeClass = EntityTypes.class;

			Field c = entityTypeClass.getDeclaredField("c");
			c.setAccessible(true);
			HashMap c_map = (HashMap) c.get(null);
			c_map.put("Sheeep", Sheeep185.class);

			Field d = entityTypeClass.getDeclaredField("d");
			d.setAccessible(true);
			HashMap d_map = (HashMap) d.get(null);
			d_map.put(Sheeep185.class, "Sheeep");

			Field e = entityTypeClass.getDeclaredField("e");
			e.setAccessible(true);
			HashMap e_map = (HashMap) e.get(null);
			e_map.put(Integer.valueOf(91), Sheeep185.class);

			Field f = entityTypeClass.getDeclaredField("f");
			f.setAccessible(true);
			HashMap f_map = (HashMap) f.get(null);
			f_map.put(Sheeep185.class, Integer.valueOf(91));

			Field g = entityTypeClass.getDeclaredField("g");
			g.setAccessible(true);
			HashMap g_map = (HashMap) g.get(null);
			g_map.put("Sheeep", Integer.valueOf(91));

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Sheeep185 spawnSheep(Plugin m, final Location t, Player target, final int color) {
		final Object w = ((CraftWorld) t.getWorld()).getHandle();
		final Sheeep185 t_ = new Sheeep185((net.minecraft.server.v1_8_R2.World) ((CraftWorld) t.getWorld()).getHandle(), (net.minecraft.server.v1_8_R2.Entity) ((CraftPlayer) target).getHandle());

		Bukkit.getScheduler().runTask(m, new Runnable() {
			public void run() {
				// t_.id = Block.getById(35);
				// t_.data = color;
				((net.minecraft.server.v1_8_R2.World) w).addEntity(t_, CreatureSpawnEvent.SpawnReason.CUSTOM);
				t_.setColor(EnumColor.fromColorIndex(color));
				t_.setPosition(t.getX(), t.getY(), t.getZ());
			}
		});

		return t_;
	}
}
