package net.plommer.BrBad.Commands;

public class TestCommand extends BaseCommand {
	
	public TestCommand() {
		bePlayer = true;
		name = "";
		argLength = 0;
		usage = "<Warp name>";
		permission = "ugh";
	}
	
	@Override
	public boolean execute() {
		
		return false;
	}
}
