package client.commandHandlers;

import client.consoleTools.ConsolePager;
import mapper.pages.Page;
import model.ComputerPreview;
import persistence.exceptions.DaoException;
import service.ComputerServiceImpl;
import ui.UiConsole;

public class ComputerListHandler implements ClientHandler {

    /**
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws DaoException an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(UiConsole ui, String[] args) throws DaoException {

        Page<ComputerPreview> page = ComputerServiceImpl.getInstance().getComputerPage();
        new ConsolePager<ComputerPreview>(ui).paginate(page);

        return true;
    }
}
