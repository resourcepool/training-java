package client.commands;
import java.util.List;

import model.CompanyModel;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class CompaniesListHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		List<CompanyModel> companiesList = service.getCompaniesList();
		
		for (CompanyModel c : companiesList) {
			ui.write(c);
		}
		return true;
	}

}
