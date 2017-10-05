package client.commands;

import java.sql.SQLException;
import client.exceptions.ClientDataFormatException;
import model.Computer;
import service.Services;
import ui.UiConsole;

public class ComputerDetailsHandler implements ClientCommand {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		
		Long id;
		
		try {
			id = Long.parseLong(args[1]);
		} catch (NumberFormatException e) {
			throw new ClientDataFormatException("Please provide an integer");
		}
		
		try {
			Computer c = service.getComputerService().getComputerDetail(id);
			ui.write(c);
			
		} catch (SQLException e) {
			ui.write(e);
		}
		return true;
	}
}
