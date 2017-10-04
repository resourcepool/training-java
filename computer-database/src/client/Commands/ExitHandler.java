package client.Commands;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ExitHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		ui.write("ok bye");
		return false;
	}
}
