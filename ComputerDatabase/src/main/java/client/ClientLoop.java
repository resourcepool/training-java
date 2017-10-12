package client;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.commandHandlers.ClientHandler;
import client.exceptions.ClientDataFormatException;
import client.exceptions.CommandsNotExistsException;
import persistence.exceptions.DaoException;
import ui.UiConsole;

public class ClientLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientLoop.class);
    private UiConsole ui;

    /**
     * @param ui User interface to show result
     */
    public ClientLoop(UiConsole ui) {
        this.ui = ui;
    }

    /**
     * loop waiting for user input and handling commands.
     */
    public void runLoop() {
        boolean loop = true;

        while (loop) {
            String input = ui.getInput();
            LOGGER.info("User input = \"" + input + "\"");

            if (!input.isEmpty() && !handleInput(ui, input)) {
                loop = false;
            }

            ui.write("---"); // end command
        }
    }

    /**
     * Handle a command and checks for errors.
     *
     * @param ui      User interface to show result
     * @param input   User input from console
     * @return true   if continue program, false if quit
     */
    private boolean handleInput(UiConsole ui, String input) {
        String key = null;

        try {
            Entry<String, ClientHandler> matchingCommand = CommandsCollection.getMatchingCommand(input);

            if (matchingCommand == null) {
                throw new CommandsNotExistsException();
            }

            key = matchingCommand.getKey();
            LOGGER.info("Matching command found = \"" + key + "\"");

            ClientHandler handler = matchingCommand.getValue();
            return handler.runCommand(ui, CommandsCollection.splitArgs(key, input));

        } catch (CommandsNotExistsException ex) {

            String notFoundMsg = String.format("Command not found : \"%s\"", input);
            LOGGER.info(notFoundMsg);
            ui.write(notFoundMsg);

        } catch (ClientDataFormatException ex) {

            LOGGER.info(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());
            ui.write(ex.getMessage());

        } catch (DaoException ex) {
            LOGGER.error(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());
            ui.write(ex.getMessage());

        } catch (Exception ex) {

            LOGGER.error(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());
            ui.write(String.format("Unexpected error, \"%s\" failed (reason \"%s\")", key, ex.getMessage()));
            ui.write(ex.getStackTrace());

        }

        return true;
    }

}
