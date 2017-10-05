package client.commands;

import java.util.List;

import model.ComputerPreview;
import service.CompanyService;
import ui.UiConsole;

public class ComputersListHandler implements ClientCommands {

	@Override
	public boolean runCommand(CompanyService service, UiConsole ui, String[] args) {
		
		List<ComputerPreview> computerList = service.getComputersList();
		
		ui.write("Id, Name");
		for (ComputerPreview c : computerList) {
			ui.write(c);
		}
		return true;
	}
}
