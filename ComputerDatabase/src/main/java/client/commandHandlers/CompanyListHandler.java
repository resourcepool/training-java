package client.commandHandlers;

import java.sql.SQLException;

import client.consoleTools.ConsolePager;
import mapper.pages.Page;
import model.Company;
import service.Services;
import ui.UiConsole;

public class CompanyListHandler implements ClientHandler {

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
            Page<Company> page = service.getCompanyService().getCompanyPage();
            new ConsolePager<Company>(ui).paginate(page);

        } catch (SQLException e) {
            ui.write(e);
        }

        return true;
    }
}
