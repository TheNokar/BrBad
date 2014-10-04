package net.plommer.BrBad.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.plommer.BrBad.Glow;
import net.plommer.BrBad.Configs.Config;

public class ItemsList {

	public static ArrayList<ItemStack> customItems = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> sr = new ArrayList<ItemStack>();
	
	public static void addRecipie(JavaPlugin plugin) {
		for(String r : Config.ItemsCustomCrafting) {
			String itc = Config.itemcooker.getString("items."+r+".output_type");
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
				if(item.containsEnchantment(Glow.getGlow())) {
					item.removeEnchantment(Glow.getGlow());
				}
				Glow.addGlow(item);
			}
			if(Config.itemcooker.getBoolean("items."+r+".furnace") == true) {
				int item_i = 0;
				int itemd = 0;
				if(isCustomItemByName(Config.itemcooker.getString("items."+r+".crafting")) != null) {
					item_i = isCustomItemByName(Config.itemcooker.getString("items."+r+".crafting")).getTypeId();
				} else {
					if(Config.itemcooker.getString("items."+r+".crafting").contains(":")) {
						item_i = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting").split(":")[0]);
						itemd = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting").split(":")[1]);
					} else {
						item_i = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting"));
					}
				}
				ItemStack itemr = new ItemStack(item_i, 1, (byte)itemd);
				FurnaceRecipe recipe = new FurnaceRecipe(item, itemr.getType());
		        plugin.getServer().addRecipe(recipe);
			} else {
				ItemStack ig1 = null;
				if(isCustomItemByName(Utils.buildString(Config.itemcooker.getString("items."+r+".crafting"))) != null) {
					ig1 = isCustomItemByName(Utils.buildString(Config.itemcooker.getString("items."+r+".crafting")));
				} else {
					if(Config.itemcooker.getString("items."+r+".crafting").contains(":")) {
						int item_i = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting").split(":")[0]);
						int itemd = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting").split(":")[1]);
						ig1 = new ItemStack(Material.getMaterial(item_i), 1, (byte)itemd);
					} else {
						int item_i = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting"));
						ig1 = new ItemStack(Material.getMaterial(item_i), 1, (byte)0);
					}
				}
				ItemStack ig2 = null;
				if(isCustomItemByName(Utils.buildString(Config.itemcooker.getString("items."+r+".crafting-2"))) != null) {
					ig2 = isCustomItemByName(Config.itemcooker.getString("items."+r+".crafting"));
				} else {
					if(Config.itemcooker.getString("items."+r+".crafting-2").contains(":")) {
						int item_i = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting-2").split(":")[0]);
						int itemd = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting-2").split(":")[1]);
						ig2 = new ItemStack(Material.getMaterial(item_i), 1, (byte)itemd);
					} else {
						int item_i = Integer.parseInt(Config.itemcooker.getString("items."+r+".crafting-2"));
						ig2 = new ItemStack(Material.getMaterial(item_i), 1, (byte)0);
					}
				}
				ShapedRecipe recipe = new ShapedRecipe(item);
				recipe.shape("W#");
				recipe.setIngredient('W', ig1.getData());
				recipe.setIngredient('#', ig2.getData());
				if(isCustomItem(ig1)) {
					sr.add(ig1);
				}
				if(isCustomItem(ig2)) {
					sr.add(ig2);
				}
		        plugin.getServer().addRecipe(recipe);
			}
			customItems.add(item);
		}
	}
	
	public static boolean isCustomItem(ItemStack item) {
		if(item == null) return false;
		for(ItemStack is : customItems) {
			if(is.getItemMeta().equals(item.getItemMeta())) {
				return true;
			}
		}
		return false;
	}

	public static ItemStack isCustomItemByName(String i) {
		for(ItemStack is : customItems) {
			if(is.getItemMeta().getDisplayName().equals(i)) {
				return is;
			}
		}
		return null;
	}
	
	public static ItemStack copStick() {
		ItemStack item = new ItemStack(Material.STICK, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("Löggu Prikið");
		item.setItemMeta(im);
		if(item.containsEnchantment(Glow.getGlow())) {
			item.removeEnchantment(Glow.getGlow());
		}
		Glow.addGlow(item);
		return item;
	}
	
	public static ItemStack zoneStick() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("The Zone Stick");
		item.setItemMeta(im);
		if(item.containsEnchantment(Glow.getGlow())) {
			item.removeEnchantment(Glow.getGlow());
		}
		Glow.addGlow(item);
		return item;
	}
	
}
