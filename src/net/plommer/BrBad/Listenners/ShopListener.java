package net.plommer.BrBad.Listenners;

import java.util.HashMap;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Shop.Shops;
import net.plommer.BrBad.Shop.Shops.Type;
import net.plommer.BrBad.Shop.ShopsUtils;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopListener implements Listener {

	protected HashMap<String, Sign> signs = new HashMap<String, Sign>();
	private JavaPlugin plugin;
	public ShopListener(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void SignEvent(SignChangeEvent event) {
		if(event.getLine(0) != null && event.getLine(1) != null) {
			if(event.getPlayer().hasPermission(Config.permission_node + "create.shop")) {
				String line1 = event.getLine(0);
				String line2 = event.getLine(1);
				String line3 = event.getLine(2);
				if(line1.equalsIgnoreCase("[sell]") && line2.length() > 0 && line3.length() > 0 || line1.equalsIgnoreCase("[buy]") && line2.length() > 0 && line3.length() > 0) {
					event.setLine(0, Utils.buildString("&0&l[Sell]"));
					event.setLine(1, Utils.buildString("&6$" + line2));
					event.setLine(2, Utils.buildString("&eClick me with"));
					event.setLine(3, Utils.buildString("&eItem to sell"));
					Type type = Shops.Type.SELL;
					if(line1.equalsIgnoreCase("[buy]")) {
						event.setLine(0, Utils.buildString("&0&l[Buy]"));
						type = Shops.Type.BUY;
					}
					signs.put(line2+":"+line3+":"+type.toString(), (Sign)event.getBlock().getState());
				}
			}
		}
	}
	
	@EventHandler
	public void InteractEvent(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = event.getClickedBlock();
			if((block != null)&&(block.getType() == Material.WALL_SIGN)) {
				if(signs.size() > 0) {
					if(event.getPlayer().hasPermission(Config.permission_node + "create.shop")) {
						for(String i : signs.keySet()) {
							Sign sign = signs.get(i);
							if(block.getLocation().equals(sign.getLocation())) {
								if(event.getPlayer().getItemInHand() != null || !event.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
									ItemStack item = event.getPlayer().getItemInHand().clone();
									int price = Integer.parseInt(i.split(":")[0]); int amount = Integer.parseInt(i.split(":")[1]);
									Type type = Shops.Type.valueOf(i.split(":")[2].toUpperCase());
									Shops shop = new Shops(block.getLocation(), item, type, price, amount, event.getPlayer());
									BrBad.db.addToShop(shop);
									shop.SetupShop(plugin);
									signs.remove(i);
									event.setCancelled(true);
								}
							}
						}
					}
				} else {
					if(event.getPlayer().hasPermission(Config.permission_node + "use.shop")) {
						Shops shop = BrBad.db.getShopAtLocation(block.getLocation());
						if(shop != null) {
							ShopsUtils.shop(shop, event.getPlayer());
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if((block != null)&&(block.getType() == Material.WALL_SIGN)) {
			Shops shop = BrBad.db.getShopAtLocation(block.getLocation());
			if(shop != null) {
				if(event.getPlayer().hasPermission(Config.permission_node + "remove.shop")) {
					BrBad.db.removeShop(shop);
					BrBad.si.get(shop.si).removeShowcase();
				} else {
					event.setCancelled(true);
				}
			}
		}
		if(BrBad.db.getShopAtLocation(block.getRelative(BlockFace.NORTH).getLocation()) != null || BrBad.db.getShopAtLocation(block.getRelative(BlockFace.SOUTH).getLocation()) != null || BrBad.db.getShopAtLocation(block.getRelative(BlockFace.WEST).getLocation()) != null || BrBad.db.getShopAtLocation(block.getRelative(BlockFace.EAST).getLocation()) != null) {
			event.setCancelled(true);
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
	
	@EventHandler
	public void chunkUnloadEvent(ChunkUnloadEvent event) {
		for(Entity e : event.getChunk().getEntities()) {
			if(e instanceof Item) {
				Item item = (Item)e;
				if(BrBad.isShowcaseItem(item)) {
					item.remove();
				}
			}
		}
	}
	
	@EventHandler
	public void chunkLoadEvent(ChunkLoadEvent event) {
		for(BlockState bs : event.getChunk().getTileEntities()) {
			if(bs instanceof Sign || bs.getType().equals(Material.WALL_SIGN) || bs.getType().equals(Material.SIGN_POST)) {
				Shops shop = BrBad.db.getShopAtLocation(bs.getLocation());
				if(shop != null) {
					shop.SetupShop(plugin);
				}
			}
		}
	}
	
}
