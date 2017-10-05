package client.commands;
import java.util.List;

import model.Company;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class CompaniesListHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		List<Company> companiesList = service.getCompaniesList();
		
		for (Company c : companiesList) {
			ui.write(c);
		}
		return true;
	}

}
