package client.commandHandlers;

import client.consoleTools.ArgsToComputer;
import client.exceptions.CompanyDontExistException;
import model.Computer;
import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;
import service.ComputerServiceImpl;
import ui.UiConsole;

public class ComputerCreationHandler implements ClientHandler {

    /**
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws DaoException an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(UiConsole ui, String[] args) throws DaoException {

        Computer model = new ArgsToComputer().createComputerFromArgs(args);

        Long idCompany = model.getCompanyId();

        if (!CompanyServiceImpl.getInstance().companyExists(idCompany)) {
            throw new CompanyDontExistException(idCompany);
        }

        Long id = ComputerServiceImpl.getInstance().createComputer(model);
        ui.write(String.format("Computer \"%s\" has been created (id: %d)", model.getName(), id));

        return true;
    }
}
