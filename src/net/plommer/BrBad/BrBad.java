package net.plommer.BrBad;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.BrBad.Commands.*;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Configs.GenerateConfigs;
import net.plommer.BrBad.CopStick.CopSafeZone;
import net.plommer.BrBad.CopStick.DrugInteractEvent;
import net.plommer.BrBad.Listenners.CraftingListener;
import net.plommer.BrBad.Listenners.ShopListener;
import net.plommer.BrBad.MySQL.DatabaseConnection;
import net.plommer.BrBad.Shop.Shops;
import net.plommer.BrBad.Showcase.ShowCaseItem;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.SetupVault;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BrBad extends JavaPlugin {
	
	public static ArrayList<ShowCaseItem> si = new ArrayList<ShowCaseItem>();
	public ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();
	public static HashMap<String, GenerateConfigs> gc = new HashMap<String, GenerateConfigs>();
	public static DatabaseConnection db;
	public static BrBad bad;
	
	public void onEnable() {
		if(getServer().getPluginManager().getPlugin("Vault") != null) {
			bad = this;
			setupConfigs();
			new Config();
			ItemsList.addRecipie(this);
			Listeners(getServer().getPluginManager());
			db = new DatabaseConnection(this);
			for(Shops shop : db.getAllShops()) {
				shop.SetupShop(this);
			}
			new SetupVault(this);
			setUpCommands();
		} else {
			getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	public void onDisable() {
		for(ShowCaseItem i : si) {
			i.removeShowcase();
		}
	}
	
	public void setUpCommands() {
		getCommand("brbad").setExecutor(new CommandHandler(this));
		commands.add(new ZoneToolCommand());
		commands.add(new ZoneCreateCommand());
		commands.add(new ReloadCommand());
		commands.add(new CopstickCommand());
	}
	
	public static void setupConfigs() {
		gc.put("config", new GenerateConfigs(BrBad.bad, "config"));
		gc.put("messages", new GenerateConfigs(BrBad.bad, "messages"));
		gc.put("item_cooker", new GenerateConfigs(BrBad.bad, "item_cooker"));
		gc.put("drug_items", new GenerateConfigs(BrBad.bad, "drug_items"));
		for(GenerateConfigs g : gc.values()) {
			g.setup();
		}
	}
	
	public static void clearConfigs() {
		gc.clear();
	}
	
	public void Listeners(PluginManager pm) {
		pm.registerEvents(new ShopListener(this), this);
		pm.registerEvents(new CraftingListener(this), this);
		pm.registerEvents(new DrugInteractEvent(), this);
	}
	
	public static boolean isShowcaseItem(Item item) {
		for(ShowCaseItem usItem : si) {
			if(usItem.isItem(item) == true) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInRegion(Location loc) {
		for (CopSafeZone z : BrBad.db.getZones()) {
			if (z.isInRegion(loc))
				return true;
			}
		return false;
	}
	
}
