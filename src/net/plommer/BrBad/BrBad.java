package net.plommer.BrBad;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.BrBad.Commands.*;
import net.plommer.BrBad.Configs.GenerateConfigs;
import net.plommer.BrBad.Listenners.ShopListener;
import net.plommer.BrBad.MySQL.DatabaseConnection;
import net.plommer.BrBad.Shop.Shops;
import net.plommer.BrBad.Showcase.ShowCaseItem;
import net.plommer.BrBad.Utils.ItemsList;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BrBad extends JavaPlugin {
	
	public static ArrayList<ShowCaseItem> si = new ArrayList<ShowCaseItem>();
	public ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();
	public static HashMap<String, GenerateConfigs> gc = new HashMap<String, GenerateConfigs>();
	public static DatabaseConnection db;
	
	public void onEnable() {
		setupConfigs();
		ItemsList.addRecipie(this);
		Listeners(getServer().getPluginManager());
		db = new DatabaseConnection(this);
		for(Shops shop : db.getAllShops()) {
			shop.SetupShop(this);
		}
	}
	
	public void onDisable() {
		for(ShowCaseItem i : si) {
			i.removeShowcase();
		}
	}
	
	public void setupConfigs() {
		gc.put("config", new GenerateConfigs(this, "config"));
		gc.put("messages", new GenerateConfigs(this, "messages"));
		gc.put("item_cooker", new GenerateConfigs(this, "item_cooker"));
		for(GenerateConfigs g : gc.values()) {
			g.setup();
		}
	}
	
	public void Listeners(PluginManager pm) {
		pm.registerEvents(new ShopListener(this), this);
	}
	
	public static boolean isShowcaseItem(Item item) {
		for(ShowCaseItem usItem : si) {
			if(usItem.isItem(item) == true) {
				return true;
			}
		}
		return false;
	}
	
}
