package client.tools;

import java.util.List;
import java.util.ListIterator;

import ui.UiConsole;

public class Page <T> {
	private UiConsole ui;
	private List<T> entities;

	public Page(UiConsole ui, List<T> entities) {
		this.ui = ui;
		this.entities = entities;
	}
	
	public void paginate(int split)
	{
		ListIterator<T> it = entities.listIterator();
		int i = 0;
		
		while (i < entities.size())
		{
			for (int j = 0; j < split; j++) {
				if (!it.hasNext())
				{
					return ;
				}
				
				ui.write(it.next().toString());
			}
			
			i += split;
			
			String msg = String.format("> continue Y/n (%d/%d) ?", i, entities.size());
			if (!ConsoleConfirm.loop(ui, msg, true))
			{
				return ;
			}
		}
	}
}
