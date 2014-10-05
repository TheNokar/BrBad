package net.plommer.BrBad.Showcase;

import java.util.Random;

import net.plommer.BrBad.BrBad;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ShowCaseItem {

	private Item item;
	private Location loc;
	private Location blockUnderLoc;
	private Material type;
	
	public ShowCaseItem(ItemStack item, Location loc, JavaPlugin plugin) {
		boolean isShowcase = false;
		for(ShowCaseItem sit : BrBad.si) {
			if(sit.getBlockUnderLoc().equals(loc) && isShowcase == false) {
				isShowcase = true;
			}
		}
		if(getItem() == null && isShowcase == false) {
			item.setAmount(1);
			Random rand = new Random();
			ItemMeta im = item.getItemMeta();
			im.setDisplayName("Displays"+rand.nextInt(500));
			item.setItemMeta(im);
			setblockUnderLoc(loc);
			setLocation(loc);
			setItem(loc.getWorld().dropItem(getLocation(), item));
			getItem().setVelocity(new Vector(0, 0.1, 0));
			getItem().setMetadata("Displays", new FixedMetadataValue(plugin, 0));
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				updatePosition();
			}
		}, 100L, 100L);
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
		loc = loc.getBlock().getLocation();
		Vector vect = loc.toVector();
		vect.add(new Vector(0.5,1.3,0.5));
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
		return item.hasMetadata("Displays");
	}
	
	public void removeShowcase() {
		this.getItem().remove();
	}
	
	public void updatePosition() {
		if (getItem() != null && getItem().getLocation().getY() <= getBlockUnderLoc().getBlockY() + 1) {
			setLocation(getBlockUnderLoc());
			getItem().teleport(getLocation());
			getItem().setVelocity(new Vector(0, 0.1, 0));
		}
	}
	
}
