package client.tools;

import ui.UiConsole;

public class ConsoleConfirm {
	
	public static Boolean loop(UiConsole ui, String msg, boolean defaultIsYes) {
		
		while (true)
		{
			ui.write(msg);
			String choice = ui.getInput().trim().toLowerCase();
			if (choice.equals(""))
			{
				return defaultIsYes;
			}
			else if (choice.equals("n"))
			{
				return false;
			}
			else if (choice.equals("y"))
			{
				return true;
			}
			else
			{
				ui.write("> Please choose a valid answer Y or N");	
			}	
		}
	}
}
