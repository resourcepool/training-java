package client;
import service.Services;
import ui.UiConsole;

public class Program {
	
	public static void main(String[] args)  
	{
		UiConsole ui = new UiConsole();
		ui.write("Welcome");
		
		try
		{
			Services service = new Services();
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
