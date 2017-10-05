package client;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

import client.commands.ClientCommand;
import service.Services;
import ui.UiConsole;

public class ClientLoop {

	private Services service;
	private UiConsole ui;
	
	public ClientLoop(Services service, UiConsole ui) {
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

	private boolean handleInput(Services service, UiConsole ui, String input) {
		String key = null;
		
		try
		{
			Entry<String, ClientCommand> matchingCommand = CommandsCollection.getMatchingCommand(input);
			if (matchingCommand == null)
			{
				throw new NoSuchElementException();
			}
			
			key = matchingCommand.getKey();
			ClientCommand handler = matchingCommand.getValue();
			
			return handler.runCommand(service, ui, splitArgs(key, input));
		}
		catch (NoSuchElementException ex)
		{
			ui.write(String.format("Command not found : \"%s\"", input));
		}
		catch (Exception ex)
		{
			ui.write(String.format("The command \"%s\" failed (reason \"%s\")", key, ex.getMessage()));
			
		}
		return true;
	}
	
	private String[] splitArgs(String key, String input) {
		
		String patternString = "^" + key + "|[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
		Pattern pattern = Pattern.compile(patternString);
        Matcher m = pattern.matcher(input);

        List<String> l = new ArrayList<>();
        while (m.find())
        {
        	String s = m.group();
			l.add(s.replaceAll("^\"|\"$", ""));
        }
        	
		return l.toArray(new String[0]);
	}
}
