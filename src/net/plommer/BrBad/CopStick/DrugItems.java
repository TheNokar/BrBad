package net.plommer.BrBad.CopStick;

import net.plommer.BrBad.Shop.ShopsUtils;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DrugItems {

	private static boolean removeItems(Inventory inv, ItemStack item) {
		if(ShopsUtils.hasItemInInventory(item, inv) > 0) {
			item.setAmount(ShopsUtils.hasItemInInventory(item, inv));
			inv.removeItem(new ItemStack[] {item});
			for(HumanEntity he : inv.getViewers()) {
				if(he instanceof Player) {
					((Player) he).updateInventory();
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean removeItemsPrivate(Inventory inv, String s) {
		if(ItemsList.isCustomItemByName(Utils.buildString(s)) != null) {
			return removeItems(inv, ItemsList.isCustomItemByName(Utils.buildString(s)));
		} else {
			int item_id = 0, item_data = 0;
			if(s.contains(":")) {
				item_id = Integer.valueOf(s.split(":")[0]); item_data = Integer.valueOf(s.split(":")[1]);
			} else {
				item_id = Integer.valueOf(s);
			}
			return removeItems(inv, new ItemStack(Material.getMaterial(item_id), 1, (byte)item_data));
		}
	}
	
}
