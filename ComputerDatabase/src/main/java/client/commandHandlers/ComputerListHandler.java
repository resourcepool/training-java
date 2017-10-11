package client.commandHandlers;

import java.sql.SQLException;

import client.consoleTools.ConsolePager;
import mapper.pages.Page;
import model.ComputerPreview;
import service.Services;
import ui.UiConsole;

public class ComputerListHandler implements ClientHandler {

    /**
     * @param service Data Access
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(Services service, UiConsole ui, String[] args) {

        try {
            Page<ComputerPreview> page = service.getComputerService().getComputerPage();
            new ConsolePager<ComputerPreview>(ui).paginate(page);
        } catch (SQLException e) {
            ui.write(e);
        }

        return true;
    }
}
