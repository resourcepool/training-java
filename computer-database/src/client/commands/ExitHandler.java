package client.commands;
import service.CompanyService;
import ui.UiConsole;

public class ExitHandler implements ClientCommands {

	@Override
	public boolean runCommand(CompanyService service, UiConsole ui, String[] args) {
		
		ui.write("ok bye");
		return false;
	}
}
