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
package com.comze_instancelabs.bedwars.sheep;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.EnumColor;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.World;

public class Register111 extends Register {

	public boolean registerEntities() {
		
		MinecraftKey localMinecraftKey = new MinecraftKey("Sheeep");
		EntityTypes.b.a(91, localMinecraftKey, Sheeep111.class);
		EntityTypes.d.add(localMinecraftKey);
		
		return true;
	}

	public Sheeep111 spawnSheep(Plugin m, final Location t, Player target, final int color) {
		final Object w = ((CraftWorld) t.getWorld()).getHandle();
		final Sheeep111 t_ = new Sheeep111((World) ((CraftWorld) t.getWorld()).getHandle(), (Entity) ((CraftPlayer) target).getHandle());

		Bukkit.getScheduler().runTask(m, new Runnable() {
			public void run() {
				// t_.id = Block.getById(35);
				// t_.data = color;
				((World) w).addEntity(t_, CreatureSpawnEvent.SpawnReason.CUSTOM);
				t_.setColor(EnumColor.fromColorIndex(color));
				t_.setPosition(t.getX(), t.getY(), t.getZ());
			}
		});

		return t_;
	}
}
