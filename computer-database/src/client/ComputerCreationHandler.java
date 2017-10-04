package client;

import model.ComputerModel;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ComputerCreationHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
//		String[] insert = input.replace(getCommands(), "").trim().split(" ");
//		String name = insert[0];
//		
//		ComputerModel model = new ComputerModel(insert, null, null, 0);
//		
		
		return true;
	}

	public String getCommands() {
		return "create new computer";
	}
}
