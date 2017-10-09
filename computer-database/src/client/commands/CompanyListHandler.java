package client.commands;
import java.sql.SQLException;
import java.util.List;

import client.tools.Page;
import model.Company;
import service.Services;
import ui.UiConsole;

public class CompanyListHandler implements ClientCommand {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		try {
			
			List<Company> companyList = service.getCompanyService().getCompanyList();
			new Page<Company>(ui, companyList).paginate(10);
			
		} catch (SQLException e) {
			ui.write(e);
		}
		
		return true;
	}
}
