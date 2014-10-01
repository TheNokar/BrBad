package net.plommer.BrBad.Showcase;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ShowCaseItem {

	private Item item;
	private Location loc;
	private Location blockUnderLoc;
	private Material type;
	
	public ShowCaseItem(ItemStack item, Location loc, short data) {
		item.setAmount(1);
		setLocation(loc);
		setblockUnderLoc(blockUnderLoc);
		setItem(loc.getWorld().dropItem(getLocation(), item));
		getItem().setVelocity(new Vector(0, 0.1, 0));
		
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public void setType(Material mat) {
		this.type = mat;
	}
	
	public void setblockUnderLoc(Location loc) {
		this.blockUnderLoc = loc;
	}
	
	public void setLocation(Location loc) {
		Vector vect = loc.toVector();
		vect.add(new Vector(0.5,0.6,0.5));
		loc = vect.toLocation(loc.getWorld());
		this.loc = loc;
		if(item != null) {
			item.teleport(loc);
		}
	}

	public Item getItem() {
		return this.item;
	}

	public Location getLocation() {
		return this.loc;
	}
	
	public Location getBlockUnderLoc() {
		return this.blockUnderLoc;
	}
	
	public Material getType() {
		return this.type;
	}
	
	public boolean isItem(Item item) {
		return item.getUniqueId() == getItem().getUniqueId();
	}
	
	public void removeShowcase() {
		this.getItem().remove();
	}
	
}
