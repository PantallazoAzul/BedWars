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

package com.comze_instancelabs.bedwars.villager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_11_R1.MerchantRecipe;

/*
 * Thanks to Howaner for his villager API! Did some minor cleanup, but great work with this.
 */

public class Merchant111 implements Merchant {
	private NMSMerchant111 h;
	private String title = null;

	public Merchant111() {
		this.h = new NMSMerchant111();
	}

	public Merchant111(String title) {
		this();
		setTitle(title);
	}

	@Override
	public void addOffer(ItemStack stack1, ItemStack stack2, ItemStack re) {
		this.addOffer(new MerchantOffer111(stack1, stack2, re));
	}

	@Override
	public void addOffer(ItemStack stack, ItemStack re) {
		this.addOffer(new MerchantOffer111(stack, re));
	}

	@Override
	public boolean hasCustomer() {
		return this.h.getCustomer() != null;
	}

	@Override
	public void openTrading(Villager villager, Player player) {
		this.h.openTrading(villager, ((CraftPlayer) player).getHandle(), this.title);
	}

	@Override
	public Merchant clone() {
		return new Merchant111().setOffers(getOffers()).setCustomer(getCustomer());
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MerchantOffer111> getOffers() {
		List<MerchantOffer111> offerList = new ArrayList<MerchantOffer111>();
		for (Object recipe : (List) this.h.getOffers(null)) {
			offerList.add(new MerchantOffer111(new NMSMerchantRecipe111((MerchantRecipe) recipe)));
		}
		return offerList;
	}

	public Merchant111 addOffer(MerchantOffer111 offer) {
		this.h.addOffer(offer.getHandle().getMerchantRecipe());
		return this;
	}

	public Merchant111 addOffers(MerchantOffer111[] offers) {
		for (MerchantOffer111 o : offers) {
			addOffer(o);
		}
		return this;
	}

	public Merchant111 setOffers(List<MerchantOffer111> offers) {
		this.h.clearRecipes();
		for (MerchantOffer111 o : offers)
			this.addOffer(o);
		return this;
	}

	public Player getCustomer() {
		return (Player) (this.h.getCustomer() == null ? null : this.h.getBukkitEntity());
	}

	public Merchant setCustomer(Player player) {
		this.h.setCustomer(player == null ? null : ((CraftPlayer) player).getHandle());
		return this;
	}

	protected NMSMerchant111 getHandle() {
		return this.h;
	}

}
