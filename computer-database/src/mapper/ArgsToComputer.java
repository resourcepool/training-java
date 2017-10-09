package mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import client.exceptions.ClientDataFormatException;
import model.Computer;

public class ArgsToComputer {
	private static final String DATE_FORMAT = "dd-MM-yyyy";

	public Computer createComputerFromArgsWithId(String[] args)
	{
		if (args.length < 6)
		{
			throw new ClientDataFormatException("Missing parameter (id, name, introduced, discontinued, idCompany)");
		}
		
		try {
			Long id = Long.parseLong(args[1]);
			return extractComputer(id, Arrays.copyOfRange(args, 2, args.length - 1));
		} 
		catch (NumberFormatException e) 
		{
			throw new ClientDataFormatException("");
		} 
	}
	
	public Computer createComputerFromArgs(String[] args)
	{
		if (args.length < 5)
		{
			throw new ClientDataFormatException("Missing parameter (name, introduced, discontinued, idCompany)");
		}
		
		return extractComputer(null, Arrays.copyOfRange(args, 1, args.length - 1));
	}

	private Computer extractComputer(Long id, String[] args) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
		String name = args[0];
		LocalDate introduced;
		LocalDate discontinued;
		Long idCompany;
		
		try {
			introduced = LocalDate.parse(args[1], dateFormat);
			discontinued = args[2].equals("null") ? null : LocalDate.parse(args[2], dateFormat);
		} catch (DateTimeParseException e) {
			throw new ClientDataFormatException("date format is " + DATE_FORMAT);
		}
		try {
			idCompany = Long.parseLong(args[3]);	
		} catch (NumberFormatException e) {
			throw new ClientDataFormatException("idCompany should be an integer corresponding to an existing company");
		}
		
		return new Computer(id, name, introduced, discontinued, idCompany);
	}
}
