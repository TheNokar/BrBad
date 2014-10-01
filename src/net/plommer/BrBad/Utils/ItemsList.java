package net.plommer.BrBad.Utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.plommer.BrBad.Glow;
import net.plommer.BrBad.Configs.Config;

public class ItemsList {

	public static ArrayList<ItemStack> customItems = new ArrayList<ItemStack>();
	
	public static void addRecipie(JavaPlugin plugin) {
		for(String r : Config.ItemsCustomCrafting) {
			String itc = Config.itemcooker.getString("items."+r+".item_type");
			int item_data = 0;
			Material item_id = null;
			if(itc.contains(":")) {
				String[] t = itc.split(":");
				item_id = Material.getMaterial(t[0]);
				item_data = Integer.parseInt(t[1]);
			} else {
				item_id = Material.getMaterial(Integer.parseInt(itc));
			}
			ItemStack item = new ItemStack(item_id, 1, (byte)item_data);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(Utils.buildString(Config.itemcooker.getString("items."+r+".item_name")));
			item.setItemMeta(im);
			if(Config.itemcooker.getBoolean("items."+r+".enchantment_glow")) {
				Glow.addGlow(item);
			}
			FurnaceRecipe recipe = new FurnaceRecipe(item, Material.getMaterial(Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting"))));
	        plugin.getServer().addRecipe(recipe);
			customItems.add(item);
		}
	}
	
	public static boolean isCustomItem(ItemStack item) {
		for(ItemStack is : customItems) {
			if(is.getItemMeta().equals(item.getItemMeta())) {
				return true;
			}
		}
		return false;
	}
	
}
