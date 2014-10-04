package net.plommer.BrBad.CopStick;

import java.util.ArrayList;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class DrugInteractEvent implements Listener {

	public static Location loc1 = null;
	public static Location loc2 = null;
	
	@EventHandler
	public void zoneThing(PlayerInteractEvent event) {
		if(event.getPlayer().getItemInHand().getItemMeta().equals(ItemsList.zoneStick().getItemMeta())) {
			if(event.getPlayer().hasPermission(Config.permission_node + "create.zone")) {
				if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					loc1 = event.getClickedBlock().getLocation();
					Utils.sendMessage(event.getPlayer(), "&eFirst pos: &a"+loc1.getBlockX()+ ", " +loc1.getBlockY()+ ", " +loc1.getBlockZ());
				}
				if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					loc2 = event.getClickedBlock().getLocation();
					Utils.sendMessage(event.getPlayer(), "&eSecond pos: &a"+loc1.getBlockX()+ ", " +loc1.getBlockY()+ ", " +loc1.getBlockZ());
				}
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void interActEvent(PlayerInteractEntityEvent event) {
		ArrayList<String> isFound = new ArrayList<String>();
		if(event.getRightClicked() instanceof Player) {
			Player player = (Player)event.getRightClicked();
			if(event.getPlayer().getItemInHand().getItemMeta().equals(ItemsList.copStick().getItemMeta())) {
				if(event.getPlayer().hasPermission(Config.permission_node + "cop")) {
					if(!BrBad.isInRegion(player.getLocation())) {
						for(String s : Config.dilist) {
							if(DrugItems.removeItemsPrivate(player.getInventory(), s)) {
								isFound.add("true");
							} else {
								isFound.add("false");
							}
						}
						if(isFound.contains("true")) {
							BrBad.db.addCopLog(player, event.getPlayer());
							Utils.sendMessage(event.getPlayer(), Config.drugs_found_cop(player));
							Utils.sendMessage(player, Config.drugs_found_player(player));
							System.out.print(Config.jail_name);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "jail " + player.getName() + " " + Config.jail_name + " " + Config.jail_time);
						} else {
							Utils.sendMessage(event.getPlayer(), Config.drugs_not_found_cop(player));
							Utils.sendMessage(player, Config.drugs_not_found_player(player));
						}
					} else {
						Utils.sendMessage(event.getPlayer(), Config.drugs_player_in_safezone(player));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(event.getPlayer().hasPermission(Config.permission_node + "cop")) {
			if(!player.getInventory().contains(ItemsList.copStick())) {
				if(player.getInventory().getItem(8) != null) {
					ItemStack a = player.getInventory().getItem(4);
					player.getInventory().addItem(a);
					player.getInventory().remove(ItemsList.copStick());
				}
				player.getInventory().setItem(4, ItemsList.copStick());
			}
		}
	}
	
}
