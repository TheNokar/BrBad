package net.plommer.BrBad.Commands;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Utils.Utils;

public class ReloadCommand extends BaseCommand {
	
	public ReloadCommand() {
		bePlayer = false;
		name = "reload:r";
		argLength = 0;
		usage = "Reloads the plugin";
		permission = "reload";
	}
	
	@Override
	public boolean execute() {
		BrBad.clearConfigs();
		BrBad.setupConfigs();
		new Config();
		Utils.sendMessage(sender, "&cBrBad configs reloaded!");
		return false;
	}
}
