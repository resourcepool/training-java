package client.commandHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.consoleTools.ConsolePager;
import mapper.pages.Page;
import model.Company;
import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;
import ui.UiConsole;

public class CompanyListHandler implements ClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyListHandler.class);
    private CompanyServiceImpl  service = CompanyServiceImpl.getInstance();

    /**
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(UiConsole ui, String[] args) {

        try {
            Page<Company> page = service.getCompanyPage();
            new ConsolePager<Company>(ui).paginate(page);

        } catch (DaoException e) {
            ui.write(e);
            LOGGER.info(e.getMessage());
        }

        return true;
    }
}
