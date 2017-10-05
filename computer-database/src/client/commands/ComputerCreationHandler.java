package client.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import client.exceptions.ClientDataFormatException;
import model.Computer;
import service.CompanyService;
import ui.UiConsole;

public class ComputerCreationHandler implements ClientCommands {

	@Override
	public boolean runCommand(CompanyService service, UiConsole ui, String[] args) throws Exception {
		
		if (args.length < 5)
		{
			throw new ClientDataFormatException("Missing parameter (name, introduced, discontinued, idCompany)"); //@to see
		}
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		
		//TODO Dirty Validation, to clean
		String name = args[1];
		LocalDate introduced = LocalDate.parse(args[1], dateFormat);
		LocalDate discontinued = args[2] == "null" ? null : LocalDate.parse(args[2], dateFormat);
		Long idCompany = Long.parseLong(args[3]);
		
		Computer model = new Computer(name, introduced, discontinued, idCompany);
		service.createComputer(model);
		return true;
	}


	public String getCommand() {
		return "create new computer";
	}
}
