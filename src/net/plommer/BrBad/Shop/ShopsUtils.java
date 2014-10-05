package net.plommer.BrBad.Shop;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopsUtils {

	
	public static Block getAttachedBlock(Block sb) {
		if (sb.getType() == Material.WALL_SIGN || sb.getType() == Material.SIGN_POST) {
			org.bukkit.material.Sign s = (org.bukkit.material.Sign) sb.getState().getData();  // org.bukkit.material.Sign
			return sb.getRelative(s.getAttachedFace());
		} else {
			return null;
		}
	}
	
	public static Location toLocation(double[] pos, String world) {
		return new Location(Bukkit.getWorld(world), pos[0], pos[1], pos[2], 0, 0);
	}
	
	public static void shop(Shops shop, Player player) {
		if(shop.getType() == Shops.Type.SELL) {
			if(removeItems(shop, player)) {
				PlayerMoneyDeposit.addMoney(player, shop.getPrice());
				BrBad.db.addShopLog(shop, player);
				Utils.sendMessage(player, Config.sold_complete(shop.getAmount(), shop.getItem(), shop.getPrice()));
			}
		} else {
			if(PlayerMoneyDeposit.hasMoney(player, shop.getPrice())) {
				BrBad.db.addShopLog(shop, player);
				addItems(shop, player);
				PlayerMoneyDeposit.removeMoney(player, shop.getPrice());
				Utils.sendMessage(player, Config.buy_complete(shop.getAmount(), shop.getItem()));
			}
		}
		player.updateInventory();
	}
	
	public static void addItems(Shops shop, Player player) {
		ItemStack item = shop.getItem();
		item.setAmount(shop.getAmount());
		player.getInventory().addItem(item);
	}
	
	public static boolean removeItems(Shops shop, Player player) {
		ItemStack item = shop.getItem();
		item.setAmount(shop.getAmount());
		if(hasItemInInventory(shop.getItem(), player.getInventory()) >= shop.getAmount()) {
			player.getInventory().removeItem(new ItemStack[] {item});
			return true;
		} else {
			Utils.sendMessage(player, Config.player_no_item);
		}
		return false;
	}
	
	public static int hasItemInInventory(ItemStack item, Inventory inv) {
		int am = 0;
		boolean isCustom = false;
		if(ItemsList.isCustomItem(item)) {
			isCustom = true;
		}
		for(ItemStack is : inv.getContents()) {
			if(is != null && isCustom == false) {
				if(is.getType().equals(item.getType()) && is.getData().equals(item.getData())) {
					am += is.getAmount();
				}
			} else if(is != null && isCustom == true) {
				if(ItemsList.isCustomItem(is) == true) {
					if(is.getItemMeta().equals(item.getItemMeta())) {
						am += is.getAmount();
					}
				}
			}
		}
		return am;
	}
	
}
