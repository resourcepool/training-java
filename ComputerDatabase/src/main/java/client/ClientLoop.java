package client;

import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.commandHandlers.ClientHandler;
import client.exceptions.ClientDataFormatException;
import client.exceptions.CommandsNotExistsException;
import persistence.exceptions.DaoException;
import service.Services;
import ui.UiConsole;

public class ClientLoop {

  private final Logger logger = LoggerFactory.getLogger(ClientLoop.class);
  private Services service;
  private UiConsole ui;

  /**
   * @param service Data access and manager
   * @param ui User interface to show result
   */
  public ClientLoop(Services service, UiConsole ui) {
    this.service = service;
    this.ui = ui;
  }

  /**
   * loop waiting for user input and handling commands.
   */
  public void runLoop() {
    boolean loop = true;

    while (loop) {
      String input = ui.getInput();
      if (!input.isEmpty() && !handleInput(service, ui, input)) {
        loop = false;
      }

      ui.write("---"); // end command
    }
  }

  /**
   * Handle a command and checks for errors.
   *
   * @param service Data access and manager
   * @param ui      User interface to show result
   * @param input   User input from console
   * @return true   if continue program, false if quit
   */
  private boolean handleInput(Services service, UiConsole ui, String input) {
    String key = null;

    try {
      Entry<String, ClientHandler> matchingCommand = CommandsCollection.getMatchingCommand(input);

      if (matchingCommand == null) {
        throw new CommandsNotExistsException();
      }

      key = matchingCommand.getKey();
      ClientHandler handler = matchingCommand.getValue();
      return handler.runCommand(service, ui, CommandsCollection.splitArgs(key, input));

    } catch (CommandsNotExistsException ex) {

      ui.write(String.format("Command not found : \"%s\"", input));

    } catch (DaoException ex) {

      ui.write(ex.getMessage());
      logger.error(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());

    } catch (ClientDataFormatException ex) {

      ui.write(ex.getMessage());

    } catch (Exception ex) {

      logger.error(ex.getMessage() + System.lineSeparator() + ex.getStackTrace());
      ui.write(String.format("Unexpected error, \"%s\" failed (reason \"%s\")", key, ex.getMessage()));
      ui.write(ex.getStackTrace());

    }

    return true;
  }

}
