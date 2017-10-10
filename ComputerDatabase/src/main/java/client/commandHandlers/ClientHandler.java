package client.commandHandlers;
import service.Services;
import ui.UiConsole;

public interface ClientHandler {
	
	boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception;
}
