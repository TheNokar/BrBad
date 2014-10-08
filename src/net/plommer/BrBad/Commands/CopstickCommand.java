package net.plommer.BrBad.Commands;

import net.plommer.BrBad.Utils.ItemsList;

public class CopstickCommand extends BaseCommand {

	public CopstickCommand() {
		bePlayer = true;
		name = "cop:cs";
		argLength = 0;
		usage = "Gives you the cop tool";
		permission = "cop";
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		player.getInventory().addItem(ItemsList.copStick());
		return false;
	}

}
