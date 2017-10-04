package client;

import java.util.ArrayList;

import model.ComputerModelPreview;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ComputersListHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		ArrayList<ComputerModelPreview> computerList = service.getComputersList();
		
		ui.write("Id, Name");
		for (ComputerModelPreview c : computerList) {
			ui.write(c);
		}
		return true;
	}
}
