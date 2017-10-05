package client.commands;

import java.sql.SQLException;
import java.util.List;

import model.ComputerPreview;
import service.Services;
import ui.UiConsole;

public class ComputerListHandler implements ClientCommand {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		
		List<ComputerPreview> computerList;
		try {
			computerList = service.getComputerService().getComputersList();

			ui.write("Id, Name");
			for (ComputerPreview c : computerList) {
				ui.write(c);
			}
		} catch (SQLException e) {
			ui.write(e);
		}
		return true;
	}
}
