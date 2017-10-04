package client.commands;

import java.util.List;

import model.ComputerModelPreview;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ComputersListHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		List<ComputerModelPreview> computerList = service.getComputersList();
		
		ui.write("Id, Name");
		for (ComputerModelPreview c : computerList) {
			ui.write(c);
		}
		return true;
	}
}
