package net.plommer.BrBad.CopStick;

import org.bukkit.Location;

public class CopSafeZone {

	private Location loc;
	private Location loc2;
	
	public CopSafeZone(Location loc1, Location loc2) {
		setLocation1(loc1);
		setLocation2(loc2);
	}

	public Location getLocation1() {
		return this.loc;
	}
	public Location getLocation2() {
		return this.loc2;
	}
	
	public void setLocation1(Location loc) {
		this.loc = loc;
	}
	public void setLocation2(Location loc) {
		this.loc2 = loc;
	}
	
	public boolean isInRegion(Location pLoc) {
		int x1 = Math.min(loc.getBlockX(), loc2.getBlockX());
		int y1 = Math.min(loc.getBlockY(), loc2.getBlockY());
		int z1 = Math.min(loc.getBlockZ(), loc2.getBlockZ());
		int x2 = Math.max(loc.getBlockX(), loc2.getBlockX());
		int y2 = Math.max(loc.getBlockY(), loc2.getBlockY());
		int z2 = Math.max(loc.getBlockZ(), loc2.getBlockZ());
		loc = new Location(loc.getWorld(), x1, y1, z1);
		loc2 = new Location(loc2.getWorld(), x2, y2, z2); 
		return pLoc.getBlockX() >= loc.getBlockX()
		&& pLoc.getBlockX() <= loc2.getBlockX()
		&& pLoc.getBlockY() >= loc.getBlockY()
		&& pLoc.getBlockY() <= loc2.getBlockY()
		&& pLoc.getBlockZ() >= loc.getBlockZ()
		&& pLoc.getBlockZ() <= loc2.getBlockZ();
	}
	
}
