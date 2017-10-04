package client;
import java.util.Set;

import service.ICompanyHandlerService;
import ui.UiConsole;

public class HelpHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		Set<String> names = CommandsCollection.getCommands().keySet();
		
		for (String name : names) {
			ui.write(String.format("> %s", name));
		}
		return true;
	}
	
}
