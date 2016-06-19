package com.comze_instancelabs.bedwars.sheep;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.EntityTargetEvent;

import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySheep;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.World;

public class Sheeep185 extends EntitySheep implements Sheeep {

	public Sheeep185(World world, Entity target) {
		super(world);
		this.locX = target.locX;
		this.locY = target.locY;
		this.locZ = target.locZ;
		try {
			Field b = this.goalSelector.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(this.goalSelector, new ArrayList());
			this.getAttributeInstance(GenericAttributes.b).setValue(128D);
			this.getAttributeInstance(GenericAttributes.d).setValue(0.37D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.goalSelector.a(0, new PathfinderGoalMeleeAttack185(this, EntityHuman.class, 1D, false));
		// this.goalSelector.a(0, new PathfinderGoalFollowParent(this, 1.1D));
		this.setGoalTarget((EntityLiving) target, EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET, false);
		((Sheep) this.getBukkitEntity()).setTarget((LivingEntity) target.getBukkitEntity());
	}

	public Location getLocation() {
		return new Location(this.world.getWorld(), this.locX, this.locY, this.locZ);
	}

}
