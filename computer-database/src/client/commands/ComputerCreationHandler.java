package client.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import client.exceptions.ClientDataFormatException;
import client.exceptions.CompanyDontExistException;
import model.Computer;
import service.Services;
import ui.UiConsole;

public class ComputerCreationHandler implements ClientCommand {

	private static final String DATE_FORMAT = "dd-MM-yyyy";

	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception {
		
		if (args.length < 5)
		{
			throw new ClientDataFormatException("Missing parameter (name, introduced, discontinued, idCompany)");
		}
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
		String name = args[1];
		LocalDate introduced;
		LocalDate discontinued;
		Long idCompany;
		
		try {
			introduced = LocalDate.parse(args[2], dateFormat);
			discontinued = args[3].equals("null") ? null : LocalDate.parse(args[3], dateFormat);
		} catch (DateTimeParseException e) {
			throw new ClientDataFormatException("date format is " + DATE_FORMAT);
		}
		try {
			idCompany = Long.parseLong(args[4]);	
		} catch (NumberFormatException e) {
			throw new ClientDataFormatException("idCompany should be an integer corresponding to an existing company");
		}
		
		if (!service.getCompanyService().companyExists(idCompany))
		{
			throw new CompanyDontExistException(idCompany);
		} 
		
		Computer model = new Computer(name, introduced, discontinued, idCompany);
		Long id = service.getComputerService().createComputer(model);
		
		ui.write(String.format("Computer \"%s\" has been created (id: %d)", name, id));
		return true;
	}
}
