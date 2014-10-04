package net.plommer.BrBad.Configs;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

public class Config {
	
	//Permissions
	public static String permission_node = "brbad.";
	
	//Configs
	public static FileConfiguration config = BrBad.gc.get("config").getCustomConfig();
	public static boolean cops_drug = config.getBoolean("cop_stick.give_cop_drugs");
	public static List<String> drug_list = config.getStringList("cop_stick.drugs");
	public static String mysql_username = config.getString("mysql.username");
	public static String mysql_password = config.getString("mysql.password");
	public static String mysql_database = config.getString("mysql.database");
	public static String mysql_host = config.getString("mysql.host");
	public static int mysql_port = config.getInt("mysql.port");
	public static String table_prefix = config.getString("mysql.table_prefix");
	public static String jail_name = config.getString("cop_stick.jail_name");	
	public static int jail_time = config.getInt("cop_stick.jail_time");	
	
	//Messages
	public static FileConfiguration msg = BrBad.gc.get("messages").getCustomConfig();
	public static String player_no_item = msg.getString("shop.player_no_item");
	public static String sold_complete(int amount, ItemStack item, int money) {
		String lol = msg.getString("shop.sold_complete");
		lol = lol.replace("{amount}", ""+amount);
		String itemn = item.getType().name().toLowerCase();
		if(ItemsList.isCustomItem(item)) {
			itemn = Utils.removeChar(item.getItemMeta().getDisplayName());
		}
		lol = lol.replace("{item}", itemn);
		lol = lol.replace("{money}", "$" + money);
		return lol;
	}
	public static String buy_complete(int amount, ItemStack item) {
		String lol = msg.getString("shop.buy_complete");
		lol = lol.replace("{amount}", ""+amount);
		String itemn = item.getType().name().toLowerCase();
		if(ItemsList.isCustomItem(item)) {
			itemn = Utils.removeChar(item.getItemMeta().getDisplayName());
		}
		lol = lol.replace("{item}", itemn);
		return lol;
	}
	
	//Cop stick
	public static String drugs_found_player(Player player) {
		String lol = msg.getString("cop_stick.drugs_found_player");
		lol = lol.replace("{cop}", player.getName());
		return lol;
	}
	public static String drugs_not_found_player(Player player) {
		String lol = msg.getString("cop_stick.drugs_not_found_player");
		lol = lol.replace("{cop}", player.getName());
		return lol;
	}
	public static String drugs_found_cop(Player player) {
		String lol = msg.getString("cop_stick.drugs_found_cop");
		lol = lol.replace("{player}", player.getName());
		return lol;
	}
	public static String drugs_not_found_cop(Player player) {
		String lol = msg.getString("cop_stick.drugs_not_found_cop");
		lol = lol.replace("{player}", player.getName());
		return lol;
	}
	public static String drugs_player_in_safezone(Player player) {
		String lol = msg.getString("cop_stick.drugs_player_in_safezone");
		lol = lol.replace("{player}", player.getName());
		return lol;
	}
	
	//Item Cooker
	public static FileConfiguration itemcooker = BrBad.gc.get("item_cooker").getCustomConfig();
	public static Set<String> ItemsCustomCrafting = itemcooker.getConfigurationSection("items").getKeys(false);
	
	//Drugs Items
	public static FileConfiguration diss = BrBad.gc.get("drug_items").getCustomConfig();
	public static List<String> dilist = diss.getStringList("drgug_stick.items");
	
}
