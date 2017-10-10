package client.commandHandlers;
import java.sql.SQLException;
import client.tools.ConsolePager;
import mapper.pages.Page;
import model.Company;
import service.Services;
import ui.UiConsole;

public class CompanyListHandler implements ClientHandler {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		try {
			Page<Company> page = service.getCompanyService().getCompanyPage();
			new ConsolePager<Company>(ui).paginate(page);
			
		} catch (SQLException e) {
			ui.write(e);
		}
		
		return true;
	}
}
