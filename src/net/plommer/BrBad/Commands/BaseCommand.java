package net.plommer.BrBad.Commands;

import java.util.ArrayList;
import java.util.List;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Utils.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand {
	public BrBad plugin;
	public CommandSender sender;
	public String cmd;
	public String name;
	
	public List<String> args = new ArrayList<String>();
	public Player player;
	public int argLength = 0;
	public String usage;
	public boolean bePlayer = true;
	public String usedCmd;
	public String permission;
	
	public boolean run(BrBad plugin, CommandSender sender, String cmd, String[] preArgs) {
		
		this.plugin = plugin;
		this.sender = sender;
		this.usedCmd = cmd;
		
		args.clear();
		for(String arg : preArgs) {
			args.add(arg);
		}
		
        for (int i = 0; i < name.split(" ").length && i < args.size(); i++) {
            args.remove(0);	
        }
		
		if(argLength > args.size()) {
			sendUsage(preArgs);
			return false;
		}
		
		if(!sender.hasPermission(this.permission())) {
			Utils.sendMessage(sender, "&cYou don't have permission to do that!");
			return false;
		}
		
		if(!(sender instanceof Player) && bePlayer) {
			return false;
		}
		if(sender instanceof Player) {
			this.player = (Player)sender;
		}
		
		return execute();		
	}
	
	public abstract boolean execute();
	
	public void sendUsage(String[] ar) {
		StringBuilder builder = new StringBuilder();
		for(String a : ar) {
			builder.append(a + " ");
		}
		Utils.sendMessage(sender, "&c/" + this.usedCmd + " " + builder.toString() + this.usage);
	}
	
	public String permission() {
		return Config.permission_node + this.permission;
	}
	
}
