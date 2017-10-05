package client.commands;
import service.Services;
import ui.UiConsole;

public interface ClientCommand {
	
	boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception;
}
