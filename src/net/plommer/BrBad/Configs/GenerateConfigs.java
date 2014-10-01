package net.plommer.BrBad.Configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GenerateConfigs {
	
	private JavaPlugin plugin;
	private String filename;
	public GenerateConfigs(JavaPlugin plugin, String filename) {
		this.plugin = plugin;
		this.filename = filename;
	}
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    	customConfigFile = new File(plugin.getDataFolder(), filename + ".yml");
	    	customConfigFile.getParentFile().mkdir();
	    	System.out.println(plugin.getDataFolder() + filename + ".yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Look for defaults in the jar
	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(plugin.getResource(filename + ".yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
			saveDefaultConfig();
	    }
	}
	
	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    return customConfig;
	}
	
	public void saveCustomConfig() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getCustomConfig().save(customConfigFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	
	public void saveDefaultConfig() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(plugin.getDataFolder(), filename + ".yml");
	    }
	    if (!customConfigFile.exists()) {    
	        plugin.saveResource(filename + ".yml", false);
	    }
	}
	
	public void setup() {
		saveDefaultConfig();
		saveCustomConfig();
		reloadCustomConfig();
	}
	
	public void reload() {
		reloadCustomConfig();
	}
	
}