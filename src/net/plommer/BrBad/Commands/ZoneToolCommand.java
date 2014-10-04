package net.plommer.BrBad.Commands;

import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

public class ZoneToolCommand extends BaseCommand {
	
	public ZoneToolCommand() {
		bePlayer = true;
		name = "zonetool:zt";
		argLength = 0;
		usage = "Gives you the zone tool";
		permission = "create";
	}
	
	@Override
	public boolean execute() {
		Utils.sendMessage(sender, "&bYou have been given the &cZone Stick");
		player.getInventory().addItem(ItemsList.zoneStick());
		return false;
	}
}
