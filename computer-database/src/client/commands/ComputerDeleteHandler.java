package client.commands;

import client.exceptions.ClientDataFormatException;
import model.Computer;
import service.ComputerServiceImpl;
import service.Services;
import ui.UiConsole;

public class ComputerDeleteHandler implements ClientCommand {
	
	@Override
	public boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception {
		
		Long id;
		try {
			id = Long.parseLong(args[1]);
		} catch (NumberFormatException e) {
			throw new ClientDataFormatException("Please enter a valid id to delete");
		}
		
		ComputerServiceImpl computerService = service.getComputerService();
		Computer c = computerService.getComputerDetail(id);
		
		Boolean valid = false;
		while (!valid)
		{
			ui.write("Are you sure you want to delete this entry ? (y/N)");
			ui.write(c);
			String choice = ui.getInput().trim().toLowerCase();
			if (choice.equals("") || choice.equals("n"))
			{
				ui.write("Canceled");
				return true;
			}
			else if (choice.equals("y"))
			{
				valid = true;
			}
			else
			{
				ui.write("Please choose a valid answer Y or N");	
			}	
		}
		
		computerService.deleteComputer(id);
		ui.write(String.format("Computer %d (\"%s\") has been deleted", 
				id, c.getName()));
		return true;
	}
}
