package client.commands;
import java.util.Set;

import client.CommandsCollection;
import service.Services;
import ui.UiConsole;

public class HelpHandler implements ClientCommand {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		
		Set<String> names = CommandsCollection.getCommands().keySet();
		
		for (String name : names) {
			ui.write(String.format("> %s", name));
		}
		return true;
	}
	
}
