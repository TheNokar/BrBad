package net.plommer.BrBad.Commands;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.CopStick.DrugInteractEvent;
import net.plommer.BrBad.Utils.Utils;

public class ZoneCreateCommand extends BaseCommand {

	public ZoneCreateCommand() {
		bePlayer = true;
		name = "zonecreate:zc";
		argLength = 0;
		usage = "Allows you to create a zone";
		permission = "create";
	}
	
	@Override
	public boolean execute() {
		if(DrugInteractEvent.loc1 != null && DrugInteractEvent.loc2 != null) {
			if(DrugInteractEvent.loc1.getWorld() == DrugInteractEvent.loc2.getWorld()) {
				BrBad.db.addZone(DrugInteractEvent.loc1, DrugInteractEvent.loc2);
				Utils.sendMessage(sender, "&eYou create a safezone!");
			}
		}
		return false;
	}
	
}
