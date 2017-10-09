package client;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import client.commands.CompanyListHandler;
import client.commands.ComputerCreationHandler;
import client.commands.ComputerDeleteHandler;
import client.commands.ComputerDetailsHandler;
import client.commands.ComputerListHandler;
import client.commands.ComputerUpdateHandler;
import client.commands.ExitHandler;
import client.commands.HelpHandler;
import service.Services;
import client.commands.ClientCommand;

import ui.UiConsole;

public class CommandsCollection {
	
	private CommandsCollection() {	}
	
	private static Map<String, ClientCommand> INSTANCE = createCommands();

	private static Map<String, ClientCommand> createCommands()
	{
		Map<String, ClientCommand> commands = new LinkedHashMap<String, ClientCommand>();
		
		CompanyListHandler companiesListHandler = new CompanyListHandler();
		ComputerListHandler computerListHandler = new ComputerListHandler();
		ComputerCreationHandler computerCreationHandler = new ComputerCreationHandler();
		ExitHandler exitHandler = new ExitHandler();
		HelpHandler helpHandler = new HelpHandler();
		ComputerDetailsHandler detailHanlder = new ComputerDetailsHandler();
		ComputerUpdateHandler updateHandler = new ComputerUpdateHandler();
		ComputerDeleteHandler deleteHandler = new ComputerDeleteHandler();
		
		commands.put("list computers", computerListHandler);
		commands.put("list companies", companiesListHandler);
		commands.put("computer details", detailHanlder);
		commands.put("get computer details", detailHanlder);
		commands.put("get details", detailHanlder);
		
		commands.put("create computer", computerCreationHandler);
		commands.put("create new computer", computerCreationHandler);
		commands.put("update computer", updateHandler);
		commands.put("delete computer", deleteHandler);
		commands.put("remove computer", deleteHandler);
		
		commands.put("exit", exitHandler);
		commands.put("quit", exitHandler);
		commands.put("bye", exitHandler);
		commands.put("help", helpHandler);
		
		ClientCommand testCmd = new ClientCommand() {
			@Override
			public boolean runCommand(Services service, UiConsole ui, String[] args) {
				ui.write("test anonymous type");
				for (String s : args) {
					ui.write(s);
				}
				return true;
			}
		};
		commands.put("testing multiple args", testCmd);
		commands.put("test multiple args", testCmd);
		commands.put("test multiple", testCmd);
		commands.put("test", testCmd);
		commands.put("test lambda", 
			(Services service, UiConsole ui, String[] args) -> {
				ui.write("test lambda");
				return true;
			}
		);
		
		return commands;
	}

	/**
	 * @return the map containing each commands, used for HelpHandler for instance
	 */
	public static Map<String, ClientCommand> getCommands() {
		return INSTANCE;
	}
	
	public static Entry<String, ClientCommand> getMatchingCommand(String inputCommands) {
		inputCommands = inputCommands.trim().toLowerCase();
		
		for (Entry<String, ClientCommand> entry : INSTANCE.entrySet()) 
		{
			if (inputCommands.startsWith( entry.getKey()))
			{
				return entry;
			}
		}
		return null;
	}
}
