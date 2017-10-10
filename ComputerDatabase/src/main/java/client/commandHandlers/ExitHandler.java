package client.commandHandlers;
import service.Services;
import ui.UiConsole;

public class ExitHandler implements ClientHandler {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		
		ui.write("ok bye");
		return false;
	}
}
