package client;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import client.commands.ClientCommand;
import client.exceptions.ClientDataFormatException;
import client.exceptions.CommandsNotExistsException;
import persistence.exceptions.DaoException;
import service.Services;
import ui.UiConsole;

public class ClientLoop {

	private final Logger logger = LoggerFactory.getLogger(ClientLoop.class);
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
				throw new CommandsNotExistsException();
			}
			
			key = matchingCommand.getKey();
			ClientCommand handler = matchingCommand.getValue();
			
			return handler.runCommand(service, ui, splitArgs(key, input));
		}
		catch (CommandsNotExistsException ex)
		{
			ui.write(String.format("Command not found : \"%s\"", input));
		}
		catch (DaoException ex)
		{
			ui.write(ex.getMessage());
			logger.error(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());
		}
		catch (ClientDataFormatException ex)
		{
			ui.write(ex.getMessage());
		}
		catch (Exception ex)
		{
			logger.error(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());
			ui.write(String.format("Unexpected error, \"%s\" failed (reason \"%s\")", key, ex.getMessage()));
			ui.write(ex.getStackTrace());
		}

		return true;
	}
	
	private String[] splitArgs(String key, String input) {
		
		String patternString = "^" + key + "|[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
		Pattern pattern = Pattern.compile(patternString);
        Matcher m = pattern.matcher(input);

        List<String> list = new ArrayList<>();
        while (m.find())
        {
        	String s = m.group();
			list.add(s.replaceAll("^\"|\"$|^'|'$", ""));
        }
        	
		return list.toArray(new String[0]);
	}
}
