package client.commandHandlers;

import client.exceptions.ClientDataFormatException;
import model.Computer;
import persistence.exceptions.DaoException;
import service.ComputerServiceImpl;
import ui.UiConsole;

public class ComputerDetailsHandler implements ClientHandler {

    /**
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws DaoException an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(UiConsole ui, String[] args) throws DaoException {

        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        Computer c;

        c = tryGetComputerById(computerService, ui, args[1]);

        if (c == null) {
            c = computerService.getComputerDetail(args[1]);
        }

        if (c == null) {
            throw new ClientDataFormatException("Error, please provide valid id/name");
        }

        ui.write(c);
        return true;
    }

    /**
     * @param service Data Access
     * @param ui Console user interface
     * @param search id to parse
     * @return false if user wants to end the program
     * @throws DaoException computer couldn't be loaded
     */
    private Computer tryGetComputerById(ComputerServiceImpl service, UiConsole ui, String search)
            throws DaoException {
        try {
            Long id = Long.parseLong(search);
            return service.getComputerDetail(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
