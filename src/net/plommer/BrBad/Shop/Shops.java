package net.plommer.BrBad.Shop;

import java.util.UUID;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Showcase.ShowCaseItem;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shops {

	public static enum Type {
		SELLING,
		BUYING
	}
	
	private Location loc;
	private ItemStack item;
	private Type type;
	private int price;
	private Player player;
	private int amount;
	
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
	
	public void SetupShop() {
		if(getAttachedBlock(loc.getBlock()).getType().equals(Material.CHEST)) {
			Sign s = (org.bukkit.block.Sign) loc.getBlock().getState();
			String dn = Utils.removeChar(getItem().getItemMeta().getDisplayName());
			if(dn == null) {
				dn = getItem().getType().name();
			}
			s.setLine(1, Utils.buildString("&e" + getAmount()));
			s.setLine(3, Utils.buildString("&a" + dn));
			s.setLine(2, Utils.buildString("&e$" + getPrice()));
			BrBad.si.add(new ShowCaseItem(getItem(), getAttachedBlock(loc.getBlock()).getLocation(), getItem().getDurability()));
			s.update();
		} else {
			Utils.sendMessage(player, "&cYou need to place the sign on a chest!");
		}
	}
	
	public Block getAttachedBlock(Block sb) {
		if (sb.getType() == Material.WALL_SIGN || sb.getType() == Material.SIGN_POST) {
			org.bukkit.material.Sign s = (org.bukkit.material.Sign) sb.getState().getData();  // org.bukkit.material.Sign
			return sb.getRelative(s.getAttachedFace());
		} else {
			return null;
		}
	}
	
	public static Location toLocation(double[] pos, String world) {
		return new Location(Bukkit.getWorld(world), pos[0], pos[1], pos[2], 0, 0);
	}
	
	public static void ShopClick(Shops shop, Player player, Type type) {
		ItemStack item = shop.getItem();
		if(player.getInventory().contains(item)) {
			player.getInventory().removeItem(new ItemStack[] {item});
		}
	}
	
}
