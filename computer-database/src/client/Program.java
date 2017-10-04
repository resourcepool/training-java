package client;
import persistence.ComputersDao;
import service.CompanyHandlerService;
import service.ICompanyHandlerService;
import ui.UiConsole;

public class Program {
	
	
	public static void main(String[] args) throws Exception {
		ComputersDao dao = new ComputersDao();
		UiConsole ui = new UiConsole();
		ICompanyHandlerService service = new CompanyHandlerService(dao, ui);


		ClientLoopHandler client = new ClientLoopHandler(service, ui);
		client.runLoop();
		
		ui.destroy();
	}
	
}
