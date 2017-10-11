package client.commandHandlers;

import client.consoleTools.ArgsToComputer;
import client.exceptions.CompanyDontExistException;
import model.Computer;
import service.Services;
import ui.UiConsole;

public class ComputerCreationHandler implements ClientHandler {

    /**
     * @param service Data Access
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception {

        Computer model = new ArgsToComputer().createComputerFromArgs(args);

        Long idCompany = model.getCompanyId();
        if (!service.getCompanyService().companyExists(idCompany)) {
            throw new CompanyDontExistException(idCompany);
        }

        Long id = service.getComputerService().createComputer(model);
        ui.write(String.format("Computer \"%s\" has been created (id: %d)", model.getName(), id));

        return true;
    }
}
