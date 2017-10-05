package client;
import persistence.ComputerDao;
import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;
import service.CompanyService;
import ui.UiConsole;

public class Program {
	
	public static void main(String[] args)  
	{
		UiConsole ui = new UiConsole();
		try
		{
			CompanyService service = new CompanyServiceImpl(ui);
			ClientLoop client = new ClientLoop(service, ui);

			client.runLoop();
		} 
		catch (Exception e)
		{
			ui.writeFatal(e);
		}
		ui.destroy();
	}
	
}
