package client;
import java.util.NoSuchElementException;

import client.commands.IClientInputHandler;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ClientLoopHandler {

	private ICompanyHandlerService service;
	private UiConsole ui;
	
	public ClientLoopHandler(ICompanyHandlerService service, UiConsole ui) {
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

	private boolean handleInput(ICompanyHandlerService service, UiConsole ui, String input) {
		boolean continueLoop = true;
		
		try
		{
			String inputCommands = input.trim().toLowerCase();
			IClientInputHandler handler = CommandsCollection.getCommand(inputCommands);
			if (handler == null)
			{
				throw new NoSuchElementException();
			}
			continueLoop = handler.runCommand(service, ui, input);
		}
		catch (NoSuchElementException ex)
		{
			ui.write(String.format("Command not found : \"%s\"", input));
		}
		catch (Exception ex)
		{
			ui.write(String.format("The command \"%s\" failed (reason \"%s\")", input, ex.getMessage()));
		}
		return continueLoop;
	}
}
