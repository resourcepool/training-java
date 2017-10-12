package client.commandHandlers;
import ui.UiConsole;

public interface ClientHandler {

    /**
     * @param ui Console user interface
     * @param args commands lines params (args[0] is the command key)
     * @return false if user wants to end the program
     * @throws Exception an unexpected error occur, handled by clientLoop
     */
    boolean runCommand(UiConsole ui, String[] args) throws Exception;
}
