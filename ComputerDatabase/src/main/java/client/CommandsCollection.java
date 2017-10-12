package client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.commandHandlers.ClientHandler;
import client.commandHandlers.CompanyListHandler;
import client.commandHandlers.ComputerCreationHandler;
import client.commandHandlers.ComputerDeleteHandler;
import client.commandHandlers.ComputerDetailsHandler;
import client.commandHandlers.ComputerListHandler;
import client.commandHandlers.ComputerUpdateHandler;
import client.commandHandlers.ExitHandler;
import client.commandHandlers.HelpHandler;

public class CommandsCollection {

    /**
     * private ctor.
     */
    private CommandsCollection() {
    }

    private static final Map<String, ClientHandler> INSTANCE = createCommands();

    /**
     * @return return the map containing all commands
     */
    private static Map<String, ClientHandler> createCommands() {
        Map<String, ClientHandler> commands = new LinkedHashMap<String, ClientHandler>();

        CompanyListHandler companiesListHandler = new CompanyListHandler();
        ComputerListHandler computerListHandler = new ComputerListHandler();
        ComputerCreationHandler computerCreationHandler = new ComputerCreationHandler();
        ExitHandler exitHandler = new ExitHandler();
        HelpHandler helpHandler = new HelpHandler();
        ComputerDetailsHandler detailHandler = new ComputerDetailsHandler();
        ComputerUpdateHandler updateHandler = new ComputerUpdateHandler();
        ComputerDeleteHandler deleteHandler = new ComputerDeleteHandler();

        commands.put("list computers", computerListHandler);
        commands.put("computers", computerListHandler);
        commands.put("list companies", companiesListHandler);
        commands.put("companies", companiesListHandler);
        commands.put("computer details", detailHandler);
        commands.put("get computer details", detailHandler);
        commands.put("get details", detailHandler);

        commands.put("create computer", computerCreationHandler);
        commands.put("create new computer", computerCreationHandler);
        commands.put("new computer", computerCreationHandler);
        commands.put("update computer", updateHandler);
        commands.put("delete computer", deleteHandler);
        commands.put("remove computer", deleteHandler);

        commands.put("exit", exitHandler);
        commands.put("quit", exitHandler);
        commands.put("bye", exitHandler);
        commands.put("help", helpHandler);

        return commands;
    }

    /**
     * @return the map containing each commands, used for HelpHandler for instance
     */
    public static Map<String, ClientHandler> getCommands() {
        return INSTANCE;
    }

    /**
     * @param inputCommands the full command typed by user
     * @return get the corresponding pair where input starts with key
     */
    public static Entry<String, ClientHandler> getMatchingCommand(String inputCommands) {
        if (inputCommands == null) {
            return null;
        }

        inputCommands = inputCommands.trim().toLowerCase();
        for (Entry<String, ClientHandler> entry : INSTANCE.entrySet()) {
            if (inputCommands.startsWith(entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    /**
     * @param key the commands key typed by user+
     * @param input the full command typed by user
     * @return the args splitted by space, only if not in quotes
     */
    public static String[] splitArgs(String key, String input) {

        String patternString = "^" + key + "|[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
        Pattern pattern = Pattern.compile(patternString);
        Matcher m = pattern.matcher(input);

        List<String> list = new ArrayList<>();
        while (m.find()) {
            String s = m.group();
            list.add(s.replaceAll("^\"|\"$|^'|'$", ""));
        }

        return list.toArray(new String[0]);
    }
}
