package client;

import service.Services;
import ui.UiConsole;

public class Program {

    /**
     * @param args unused
     */
    public static void main(String[] args) {
        UiConsole ui = new UiConsole();
        ui.write("Welcome, press \"help\" to see available commands");

        try {
            Services service = new Services();
            ClientLoop client = new ClientLoop(service, ui);

            client.runLoop();
        } catch (Exception e) {
            ui.writeFatal(e);
        }
        ui.destroy();
    }

}
