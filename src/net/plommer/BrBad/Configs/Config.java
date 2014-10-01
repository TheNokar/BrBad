package net.plommer.BrBad.Configs;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import net.plommer.BrBad.BrBad;

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
	
	//Messages
	
	//Item Cooker
	public static FileConfiguration itemcooker = BrBad.gc.get("item_cooker").getCustomConfig();
	public static Set<String> ItemsCustomCrafting = itemcooker.getConfigurationSection("items").getKeys(false);
}
