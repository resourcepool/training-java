package client.commandHandlers;
import service.Services;
import ui.UiConsole;

public class ExitHandler implements ClientHandler {

    /**
     * @param service Data Access
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    @Override
     public boolean runCommand(Services service, UiConsole ui, String[] args) {

         ui.write("ok bye");
         return false;
     }
}
