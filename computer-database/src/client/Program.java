package client;
import persistence.ComputersDao;
import service.CompanyHandlerService;
import service.CompanyHandlerServiceDummy;
import service.ICompanyHandlerService;
import ui.UiConsole;

@SuppressWarnings("unused")
public class Program {
	
	
	public static void main(String[] args) throws Exception {
		ComputersDao dao = new ComputersDao();
		UiConsole ui = new UiConsole();
		ICompanyHandlerService service = new CompanyHandlerService(dao, ui);

//		ICompanyHandlerService service = new CompanyHandlerServiceDummy();

		ClientLoopHandler client = new ClientLoopHandler(service, ui);
		client.runLoop();
		
		ui.destroy();
	}
	
}
