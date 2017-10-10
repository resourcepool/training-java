package client.tools;

import mapper.exceptions.PageException;
import mapper.pages.Page;
import ui.UiConsole;

public class ConsolePager <T> {
	private UiConsole ui;

	public ConsolePager(UiConsole ui) {
		this.ui = ui;
	}
	
	public void paginate(Page<T> p)
	{
		while (p.hasNext())
		{
			try
			{
				p = p.next();
				ui.write(p.dump());
			}
			catch (PageException ex)
			{
				ui.write("> Page couldn't be loaded");
				ui.write(ex);
				return ;
			}
			
			if (!p.hasNext())
			{
				ui.write("> Finished");
				return ;
			}
			String msg = String.format("> continue Y/n (%d/%d) ?", p.getCurrentCount() + p.getPageSize(), p.getTotalCount());
			if (!ConsoleConfirm.loop(ui, msg, true)) return ;
		}
	}
}
