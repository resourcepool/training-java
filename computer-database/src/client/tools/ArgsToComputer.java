package client.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import client.exceptions.ClientDataFormatException;
import model.Computer;

public class ArgsToComputer {
	private static final String DATE_FORMAT = "dd-MM-yyyy";
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);

	public Computer createComputerFromArgsWithId(String[] args)
	{
		if (args.length < 6)
		{
			throw new ClientDataFormatException("Missing parameter (id, name, introduced, discontinued, idCompany)");
		}
		
		try {
			Long id = Long.parseLong(args[1]);
			return extractComputer(id, Arrays.copyOfRange(args, 2, args.length));
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
		
		return extractComputer(null, Arrays.copyOfRange(args, 1, args.length));
	}

	private Computer extractComputer(Long id, String[] args) {
		String name = args[0];
		LocalDate introduced;
		LocalDate discontinued;
		Long idCompany;
		
		try {
			introduced = extractDate(args[1]);
			discontinued = extractDate(args[2]);
		} 
		catch (DateTimeParseException e) {
			throw new ClientDataFormatException("date format is " + DATE_FORMAT);
		}
		
		if (isLong(args[3]))
		{
			idCompany = Long.parseLong(args[3]);
		}
		else
		{
			throw new ClientDataFormatException("idCompany should be an integer corresponding to an existing company");
		}
		
		return new Computer(id, name, introduced, discontinued, idCompany);
	}
	
	private boolean isLong(String s) {
		if (s == null) 
		{
		    return false;
		}
		int size = s.length();
		for (int i = 0; i < size; i++) 
		{
		    if (Character.isDigit(s.charAt(i)) == false) 
		    {
		        return false;
		    }
		}
	    return true;
	}

	private LocalDate extractDate(String s)
	{
		if (s.isEmpty() || s.equals("null"))
		{
			return null;
		}
		else
		{
			return LocalDate.parse(s, dateFormat);	
		}
	}
}
