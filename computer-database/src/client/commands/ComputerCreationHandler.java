package client.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import client.exceptions.ClientDataFormatException;
import model.Computer;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ComputerCreationHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) throws Exception {
		
		String[] parameters = extractParameters(input);
		if (parameters.length < 4)
		{
			throw new ClientDataFormatException("Missing parameter (name, introduced, discontinued, idCompany)"); //@to see
		}
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		
		//TODO Dirty Validation
		String name = parameters[0];
		LocalDate introduced = LocalDate.parse(parameters[1], dateFormat);
		LocalDate discontinued = parameters[2] == "null" ? null : LocalDate.parse(parameters[2], dateFormat);
		Long idCompany = Long.parseLong(parameters[3]);
		
		Computer model = new Computer(name, introduced, discontinued, idCompany);
		service.createComputer(model);
		return true;
	}

	private String[] extractParameters(String input) {
		
		String parameters = input.replace(getCommand(), "").trim();
		
		Pattern pattern = Pattern.compile("(\"[^\"]+\")|\\S+");
        return pattern.split(parameters);
	}

	public String getCommand() {
		return "create new computer";
	}
}
