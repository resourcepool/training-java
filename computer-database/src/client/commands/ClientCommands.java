package client.commands;
import service.CompanyService;
import ui.UiConsole;

public interface ClientCommands {
	
	boolean runCommand(CompanyService service, UiConsole ui, String[] args) throws Exception;
}
