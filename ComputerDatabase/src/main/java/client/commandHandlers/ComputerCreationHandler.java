package client.commandHandlers;

import client.exceptions.CompanyDontExistException;
import client.tools.ArgsToComputer;
import model.Computer;
import service.Services;
import ui.UiConsole;

public class ComputerCreationHandler implements ClientHandler {

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception {
		
		Computer model = new ArgsToComputer().createComputerFromArgs(args);
		
		Long idCompany = model.getCompanyId();
		if (!service.getCompanyService().companyExists(idCompany))
		{
			throw new CompanyDontExistException(idCompany);
		} 
		
		Long id = service.getComputerService().createComputer(model);
		ui.write(String.format("Computer \"%s\" has been created (id: %d)", 
				model.getName(), id));
		
		return true;
	}
}
