package client.commands;
import java.util.Set;

import client.CommandsCollection;
import service.CompanyService;
import ui.UiConsole;

public class HelpHandler implements ClientCommands {

	@Override
	public boolean runCommand(CompanyService service, UiConsole ui, String[] args) {
		
		Set<String> names = CommandsCollection.getCommands().keySet();
		
		for (String name : names) {
			ui.write(String.format("> %s", name));
		}
		return true;
	}
	
}
