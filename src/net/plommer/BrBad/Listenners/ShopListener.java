package net.plommer.BrBad.Listenners;

import java.util.ArrayList;
import java.util.HashMap;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Shop.Shops;
import net.plommer.BrBad.Showcase.ShowCaseItem;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

	protected HashMap<Integer, Sign> signs = new HashMap<Integer, Sign>();
	
	@EventHandler
	public void SignEvent(SignChangeEvent event) {
		if(event.getLine(0) != null && event.getLine(1) != null) {
			String line1 = event.getLine(0);
			String line2 = event.getLine(1);
			if(line1.equalsIgnoreCase("[sell]") && line2.length() > 0) {
				event.setLine(0, Utils.buildString("&b[Sell]"));
				event.setLine(1, Utils.buildString("&6$" + line2));
				event.setLine(2, Utils.buildString("&eClick me with"));
				event.setLine(3, Utils.buildString("&eItem to sell"));
				signs.put(Integer.parseInt(line2), (Sign)event.getBlock().getState());
			}
		}
	}
	
	@EventHandler
	public void InteractEvent(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(signs.size() > 0) {
				Block block = event.getClickedBlock();
				if((block.getState() instanceof Sign)&&(block != null)&&(block.getType() == Material.WALL_SIGN)) {
					for(int i : signs.keySet()) {
						Sign sign = signs.get(i);
						if(block.getLocation().equals(sign.getLocation())) {
							if(event.getPlayer().getItemInHand() != null || !event.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
								ItemStack item = event.getPlayer().getItemInHand().clone();
								BrBad.db.addToShop(new Shops(block.getLocation(), item, Shops.Type.SELLING, i, event.getPlayer()));
								signs.remove(i);
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void deSpawnEvent(ItemDespawnEvent event) {
		if(BrBad.isShowcaseItem(event.getEntity()) == true) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void pickUpEvent(PlayerPickupItemEvent event) {
		if(BrBad.isShowcaseItem(event.getItem()) == true) {
			event.setCancelled(true);
		}
	}
	
}
