package client;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

import client.commands.ClientCommands;
import service.CompanyService;
import ui.UiConsole;

public class ClientLoop {

	private CompanyService service;
	private UiConsole ui;
	
	public ClientLoop(CompanyService service, UiConsole ui) {
		this.service = service;
		this.ui = ui;
	}
	
	public void runLoop()
	{
		boolean loop = true;
		
		while (loop)
		{
			String input = ui.getInput();
			if (!input.isEmpty() && !handleInput(service, ui, input))
			{
				loop = false;
			}
			
			ui.write("---"); //end command
		}
	}

	private boolean handleInput(CompanyService service, UiConsole ui, String input) {
		try
		{
			Entry<String, ClientCommands> matchingCommand = CommandsCollection.getMatchingCommand(input);
			if (matchingCommand == null)
			{
				throw new NoSuchElementException();
			}
			
			String key = matchingCommand.getKey();
			ClientCommands handler = matchingCommand.getValue();
			
			return handler.runCommand(service, ui, splitArgs(key, input));
		}
		catch (NoSuchElementException ex)
		{
			ui.write(String.format("Command not found : \"%s\"", input));
		}
		catch (Exception ex)
		{
			ui.write(String.format("The command \"%s\" failed (reason \"%s\")", input, ex.getMessage()));
		}
		return true;
	}
	
	private String[] splitArgs(String key, String input) {
		
		String patternString = "^" + key + "|[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
		Pattern pattern = Pattern.compile(patternString);
        Matcher m = pattern.matcher(input);

        List<String> l = new ArrayList<>();
        while (m.find())
        	l.add(m.group());
        
		return l.toArray(new String[0]);
	}
}
