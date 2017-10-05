package client.commands;

import java.util.List;

import model.ComputerPreview;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ComputersListHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		List<ComputerPreview> computerList = service.getComputersList();
		
		ui.write("Id, Name");
		for (ComputerPreview c : computerList) {
			ui.write(c);
		}
		return true;
	}
}
