package client;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import client.commands.CompaniesListHandler;
import client.commands.ComputerCreationHandler;
import client.commands.ComputersListHandler;
import client.commands.ExitHandler;
import client.commands.HelpHandler;
import client.commands.ClientCommands;

import service.CompanyService;
import ui.UiConsole;

public class CommandsCollection {
	
	private CommandsCollection() {	}
	
	private static Map<String, ClientCommands> INSTANCE = createCommands();

	private static Map<String, ClientCommands> createCommands()
	{
		Map<String, ClientCommands> commands = new LinkedHashMap<String, ClientCommands>();
		
		CompaniesListHandler companiesListHandler = new CompaniesListHandler();
		ComputersListHandler computerListHandler = new ComputersListHandler();
		ComputerCreationHandler computerCreationHandler = new ComputerCreationHandler();
		ExitHandler exitHandler = new ExitHandler();
		HelpHandler helpHandler = new HelpHandler();
		
		commands.put("list computers", computerListHandler);
		commands.put("list companies", companiesListHandler);
		commands.put(computerCreationHandler.getCommand(), computerCreationHandler);
		commands.put("create computer", computerCreationHandler);
		
		commands.put("exit", exitHandler);
		commands.put("quit", exitHandler);
		commands.put("bye", exitHandler);
		commands.put("help", helpHandler);
		
		ClientCommands test = new ClientCommands() {
			@Override
			public boolean runCommand(CompanyService service, UiConsole ui, String[] args) {
				ui.write("test anonymous type");
				for (String s : args) {
					ui.write(s);
				}
				return true;
			}
		};
		commands.put("testing multiple args", test);
		commands.put("test multiple args", test);
		commands.put("test", test);
		commands.put("test lambda", 
			(CompanyService service, UiConsole ui, String[] args) -> {
				ui.write("test lambda");
				return true;
			}
		);
		
		return commands;
	}

	/**
	 * @return the map containing each commands, used for HelpHandler for instance
	 */
	public static Map<String, ClientCommands> getCommands() {
		return INSTANCE;
	}
	
	public static Entry<String, ClientCommands> getMatchingCommand(String inputCommands) {
		inputCommands = inputCommands.trim().toLowerCase();
		
		for (Entry<String, ClientCommands> entry : INSTANCE.entrySet()) 
		{
			if (inputCommands.startsWith( entry.getKey()))
			{
				return entry;
			}
		}
		return null;
	}
}
