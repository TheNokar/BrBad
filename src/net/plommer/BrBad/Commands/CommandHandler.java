package net.plommer.BrBad.Commands;

import net.plommer.BrBad.BrBad;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
	private BrBad plugin;
	public CommandHandler(BrBad plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {
		outer:
		for (BaseCommand command : plugin.commands.toArray(new BaseCommand[0])) {
			String[] cmds = null;
			int cmdl = 0;
			int argl = 0;
			if(args.length > 0) {
				cmds = command.name.split(" ");
				cmdl = cmds.length;
				argl = args.length;
			} else {
				cmdl = 0;
				argl = 1;
			}
			
			for (int i = 0; i < cmdl; i++) {
				if(cmds[i].contains(":")) {
					String[] c = cmds[i].split(":");
					for(String cm : c) {
						if(cm.equalsIgnoreCase(args[i])) {
							cmds[i] = cm;
						}
					}
				}
				if (i >= argl || !cmds[i].equalsIgnoreCase(args[i])) continue outer;
			}
			return command.run(plugin, sender, commandLabel, args);
		}
		return true;
	}
}
