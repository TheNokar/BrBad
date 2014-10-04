package net.plommer.BrBad.Shop;

import java.util.UUID;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Showcase.ShowCaseItem;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Shops {

	public static enum Type {
		SELL,
		BUY
	}
	
	private Location loc;
	private ItemStack item;
	private Type type;
	private int price;
	private Player player;
	private int amount;
	public int si;
	
	public Shops(Location loc, ItemStack item, Type type, int price, int amount, Player player) {
		setLocation(loc);
		setItem(item);
		setType(type);
		setPrice(price);
		setAmount(amount);
		this.player = player;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	public int getAmount() {
		return this.amount;
	}
	public UUID getPlayerUUID() {
		return this.player.getUniqueId();
	}
	public int getPrice() {
		return this.price;
	}
	public ItemStack getItem() {
		return this.item;
	}
	public Type getType() {
		return this.type;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	public void setItem(ItemStack item) {
		this.item = item;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public void SetupShop(JavaPlugin plugin) {
		if(ShopsUtils.getAttachedBlock(loc.getBlock()) != null && ShopsUtils.getAttachedBlock(loc.getBlock()).getLocation().add(0,1,0).getBlock().getType() == Material.AIR) {
			Sign s = (org.bukkit.block.Sign) loc.getBlock().getState();
			String dn = Utils.removeChar(getItem().getItemMeta().getDisplayName());
			if(dn == null) {
				dn = getItem().getType().name();
			}
			s.setLine(2, Utils.buildString("&0" + getAmount()));
			s.setLine(3, Utils.buildString("&0" + dn));
			s.setLine(1, Utils.buildString("&3&n$" + getPrice()));
			si = BrBad.si.size();
			BrBad.si.add(new ShowCaseItem(getItem().clone(), ShopsUtils.getAttachedBlock(loc.getBlock()).getLocation(), plugin));
			s.update();
		} else {
			System.out.print("This dosen't seem to be working :/");
		}
	}
	
}
