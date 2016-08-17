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

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.MerchantRecipe;

/*
 * Thanks to Howaner for his villager API! Did some minor cleanup, but great work with this.
 */

public class Merchant188 implements Merchant {
	private NMSMerchant188 h;
	private String title = null;

	public Merchant188() {
		this.h = new NMSMerchant188();
	}

	public Merchant188(String title) {
		this();
		setTitle(title);
	}

	@Override
	public void addOffer(ItemStack stack1, ItemStack stack2, ItemStack re) {
		this.addOffer(new MerchantOffer188(stack1, stack2, re));
	}

	@Override
	public void addOffer(ItemStack stack, ItemStack re) {
		this.addOffer(new MerchantOffer188(stack, re));
	}

	@Override
	public boolean hasCustomer() {
		return this.h.getCustomer() != null;
	}

	@Override
	public void openTrading(Villager villager, Player player) {
		this.h.openTrading(villager, ((CraftPlayer)player).getHandle(), this.title);
	}

	@Override
	public Merchant clone() {
		return new Merchant188().setOffers(getOffers()).setCustomer(getCustomer());
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MerchantOffer188> getOffers() {
		List<MerchantOffer188> offerList = new ArrayList<MerchantOffer188>();
		for (Object recipe : (List) this.h.getOffers(null)) {
			offerList.add(new MerchantOffer188(new NMSMerchantRecipe188((MerchantRecipe) recipe)));
		}
		return offerList;
	}

	public Merchant188 addOffer(MerchantOffer188 offer) {
		this.h.addOffer(offer.getHandle().getMerchantRecipe());
		return this;
	}

	public Merchant188 addOffers(MerchantOffer188[] offers) {
		for (MerchantOffer188 o : offers) {
			addOffer(o);
		}
		return this;
	}

	public Merchant188 setOffers(List<MerchantOffer188> offers) {
		this.h.clearRecipes();
		for (MerchantOffer188 o : offers)
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

	protected NMSMerchant188 getHandle() {
		return this.h;
	}

}
