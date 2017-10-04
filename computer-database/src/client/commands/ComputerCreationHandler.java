package client.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import model.ComputerModel;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class ComputerCreationHandler implements IClientInputHandler {

	@Override
	public boolean runCommand(ICompanyHandlerService service, UiConsole ui, String input) throws Exception {
		
		String[] parameters = extractParameters(input);
		if (parameters.length < 4)
		{
			throw new Exception("Missing parameter"); //@to see
		}
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-mm-yyyy");
		
		String name = parameters[0];
		Date introduced = dateformat.parse(parameters[1]);
		Date discontinued = parameters[2] == "null" ? null : dateformat.parse(parameters[3]);
		long id_company = Long.parseLong(parameters[3]);
		
		ComputerModel model = new ComputerModel(name, introduced, discontinued, id_company);
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
