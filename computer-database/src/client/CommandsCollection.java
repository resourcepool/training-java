package client;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import client.commands.CompaniesListHandler;
import client.commands.ComputerCreationHandler;
import client.commands.ComputersListHandler;
import client.commands.ExitHandler;
import client.commands.HelpHandler;
import client.commands.IClientInputHandler;

import java.util.Set;

import service.ICompanyHandlerService;
import ui.UiConsole;

public class CommandsCollection {
	
	private CommandsCollection() {	}
	
	private static Map<String, IClientInputHandler> INSTANCE = createCommands();

	private static Map<String, IClientInputHandler> createCommands()
	{
		Map<String, IClientInputHandler> commands = new HashMap<String, IClientInputHandler>();
		
		CompaniesListHandler companiesListHandler = new CompaniesListHandler();
		ComputersListHandler computerListHandler = new ComputersListHandler();
		ComputerCreationHandler computerCreationHandler = new ComputerCreationHandler();
		ExitHandler exitHandler = new ExitHandler();
		HelpHandler helpHandler = new HelpHandler();
		
		commands.put("list computers", computerListHandler);
		commands.put("list companies", companiesListHandler);
		commands.put(computerCreationHandler.getCommand(), computerCreationHandler);
		
		commands.put("exit", exitHandler);
		commands.put("quit", exitHandler);
		commands.put("bye", exitHandler);
		commands.put("help", helpHandler);
		
		commands.put("test", new IClientInputHandler() {
			@Override
			public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
				ui.write("test");
				return true;
			}
		});
		
		return commands;
	}


	public static Map<String, IClientInputHandler> getCommands() {
		return INSTANCE;
	}

	public static IClientInputHandler getCommand(String inputCommands) {
		for (Entry<String, IClientInputHandler> entry : INSTANCE.entrySet()) 
		{
			if (inputCommands.startsWith( entry.getKey()))
			{
				return entry.getValue();
			}
		}
		return null;
	}
}
