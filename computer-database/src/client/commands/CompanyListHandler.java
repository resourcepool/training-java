package client.commands;
import java.sql.SQLException;
import java.util.List;

import model.Company;
import service.Services;
import ui.UiConsole;

public class CompanyListHandler implements ClientCommand {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) {
		try {
			
			List<Company> companyList = service.getCompanyService().getCompanyList();
			
			for (Company c : companyList) {
				ui.write(c);
			}
			
		} catch (SQLException e) {
			ui.write(e);
		}
		
		return true;
	}
}
