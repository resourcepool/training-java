package client.commands;
import java.util.List;

import model.Company;
import service.CompanyService;
import ui.UiConsole;

public class CompaniesListHandler implements ClientCommands {

	@Override
	public boolean runCommand(CompanyService service, UiConsole ui, String[] args) {
		
		List<Company> companiesList = service.getCompaniesList();
		
		for (Company c : companiesList) {
			ui.write(c);
		}
		return true;
	}

}
