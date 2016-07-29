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

import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_7_R2.MerchantRecipe;

/*
 * Thanks to Howaner for his villager API! Did some minor cleanup, but great work with this.
 */

public class Merchant175 implements Merchant {
	private NMSMerchant175 h;
	private String title = null;

	public Merchant175() {
		this.h = new NMSMerchant175();
	}

	public Merchant175(String title) {
		this();
		setTitle(title);
	}

	@Override
	public void addOffer(ItemStack stack1, ItemStack stack2, ItemStack re) {
		this.addOffer(new MerchantOffer175(stack1, stack2, re));
	}

	@Override
	public void addOffer(ItemStack stack, ItemStack re) {
		this.addOffer(new MerchantOffer175(stack, re));
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
		return new Merchant175().setOffers(getOffers()).setCustomer(getCustomer());
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MerchantOffer175> getOffers() {
		List<MerchantOffer175> offerList = new ArrayList<MerchantOffer175>();
		for (Object recipe : (List) this.h.getOffers(null)) {
			offerList.add(new MerchantOffer175(new NMSMerchantRecipe175((MerchantRecipe) recipe)));
		}
		return offerList;
	}

	public Merchant175 addOffer(MerchantOffer175 offer) {
		this.h.addOffer(offer.getHandle().getMerchantRecipe());
		return this;
	}

	public Merchant175 addOffers(MerchantOffer175[] offers) {
		for (MerchantOffer175 o : offers) {
			addOffer(o);
		}
		return this;
	}

	public Merchant175 setOffers(List<MerchantOffer175> offers) {
		this.h.clearRecipes();
		for (MerchantOffer175 o : offers)
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

	protected NMSMerchant175 getHandle() {
		return this.h;
	}

}
