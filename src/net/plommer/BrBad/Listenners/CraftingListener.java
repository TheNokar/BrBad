package net.plommer.BrBad.Listenners;

import net.plommer.BrBad.Utils.ItemsList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingListener implements Listener {
	
	private JavaPlugin plugin;
	public CraftingListener(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void CraftingPre(PrepareItemCraftEvent event) {
		ItemStack result = event.getInventory().getResult();
		if(event.getRecipe() instanceof ShapedRecipe) {
			for(ItemStack ir : ItemsList.sr) {
				if(ItemsList.isCustomItem(ir)) {
					for(ItemStack ugh : event.getInventory().getContents()) {
						if(ItemsList.isCustomItem(ugh)) {
							event.getInventory().setResult(null);
							if(ir == ugh || ugh.getType() == ir.getType()) {
								event.getInventory().setResult(result);
								((Player) event.getView().getPlayer()).updateInventory();
							}
						}
					}
				}
			}
			for(ItemStack ugh : event.getInventory().getContents()) {
				if(ItemsList.isCustomItem(ugh) && !ItemsList.isCustomItem(event.getInventory().getResult())) {
					event.getInventory().setResult(null);
					((Player) event.getView().getPlayer()).updateInventory();
				}
			}
		}
	}
	
	@EventHandler
	public void CraftingEvent(final CraftItemEvent event) {
		 Bukkit.getServer().getScheduler()
         .scheduleSyncDelayedTask(plugin, new Runnable() {
             @Override
             public void run() {
                 ((Player) event.getWhoClicked()).updateInventory();
             }
         }, 1);
	}
	
}
