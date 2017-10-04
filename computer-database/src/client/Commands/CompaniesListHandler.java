package client.Commands;
import java.util.ArrayList;

import model.CompanyModel;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class CompaniesListHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) {
		
		ArrayList<CompanyModel> companiesList = service.getCompaniesList();
		
		for (CompanyModel c : companiesList) {
			ui.write(c);
		}
		return true;
	}

}
