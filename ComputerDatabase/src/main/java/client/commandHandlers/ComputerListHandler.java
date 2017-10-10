package client.commandHandlers;

import java.sql.SQLException;
import client.tools.ConsolePager;
import mapper.pages.Page;
import model.ComputerPreview;
import service.Services;
import ui.UiConsole;

public class ComputerListHandler implements ClientHandler {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		
		try {
			Page<ComputerPreview> page = service.getComputerService().getComputerPage();
			new ConsolePager<ComputerPreview>(ui).paginate(page);
		} catch (SQLException e) {
			ui.write(e);
		}
		
		return true;
	}
}
