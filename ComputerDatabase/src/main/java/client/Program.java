package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ui.UiConsole;

public class Program {

    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);

    /**
     * @param args unused
     */
    public static void main(String[] args) {

        UiConsole ui = new UiConsole();
        ui.write("Welcome, press \"help\" to see available commands");

        try {
            ClientLoop client = new ClientLoop(ui);

            client.runLoop();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            ui.writeFatal(e);
        }
        ui.destroy();
    }

}
