package client;
import service.ICompanyHandlerService;
import ui.UiConsole;

public interface IClientInputHandler {
	
	boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) throws Exception;
}
