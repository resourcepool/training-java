package client.commandHandlers;

import java.sql.SQLException;

import client.exceptions.ClientDataFormatException;
import model.Computer;
import service.ComputerServiceImpl;
import service.Services;
import ui.UiConsole;

public class ComputerDetailsHandler implements ClientHandler {

    /**
     * @param service Data Access
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(Services service, UiConsole ui, String[] args) {

        ComputerServiceImpl computerService = service.getComputerService();
        Computer c;

        try {
            c = tryGetComputerById(computerService, ui, args[1]);

            if (c == null) {
                c = computerService.getComputerDetail(args[1]);
            }

        } catch (SQLException e) {
            ui.write(e);
            return true;
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
     * @throws SQLException computer couldn't be loaded
     */
    private Computer tryGetComputerById(ComputerServiceImpl service, UiConsole ui, String search) throws SQLException {
        try {
            Long id = Long.parseLong(search);
            return service.getComputerDetail(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
