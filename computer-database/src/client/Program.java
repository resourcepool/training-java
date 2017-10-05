package client;
import persistence.ComputerDao;
import persistence.exceptions.DaoException;
import service.CompanyHandlerService;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class Program {
	
	public static void main(String[] args)  
	{
		UiConsole ui = new UiConsole();
		try
		{
			ICompanyHandlerService service = new CompanyHandlerService(ui);
			ClientLoopHandler client = new ClientLoopHandler(service, ui);

			client.runLoop();
		} 
		catch (Exception e)
		{
			ui.writeFatal(e);
		}
		ui.destroy();
	}
	
}
