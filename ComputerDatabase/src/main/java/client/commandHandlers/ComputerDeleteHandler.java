package client.commandHandlers;

import client.consoleTools.ConsoleConfirm;
import client.exceptions.ClientDataFormatException;
import model.Computer;
import service.ComputerServiceImpl;
import service.Services;
import ui.UiConsole;

public class ComputerDeleteHandler implements ClientHandler {

    /**
     * @param service Data Access
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    @Override
    public boolean runCommand(Services service, UiConsole ui, String[] args) throws Exception {

        Long id;
        try {
            id = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            throw new ClientDataFormatException("Please enter a valid id to delete");
        }

        ComputerServiceImpl computerService = service.getComputerService();
        Computer c = computerService.getComputerDetail(id);
        if (c == null) {
            throw new ClientDataFormatException("No computer possess this Id");
        }

        if (!ConsoleConfirm.loop(ui,
                "> Are you sure you want to delete this entry ? (y/N)" + System.lineSeparator() + c.toString(),
                false)) {
            ui.write("Canceled");
            return true;
        }

        computerService.deleteComputer(id);
        ui.write(String.format("Computer %d (\"%s\") has been deleted", id, c.getName()));
        return true;
    }

}
