import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Map.Entry;

import org.junit.Test;

import client.CommandsCollection;
import client.commandHandlers.ClientHandler;

public class CommandsCollectionTest {

    /**
     * test command line parsing from console with and w/o quote.
     */
    @Test
    public void testArgsParsingWithQuotes() {
        String key = "create computer";
        String input = "create computer \"hello 'world\" \"''\" '\"\"' 12";

        String[] args = CommandsCollection.splitArgs(key, input);

        assertEquals(args[0], key);
        assertEquals(args[1], "hello 'world");
        assertEquals(args[2], "''");
        assertEquals(args[3], "\"\"");
        assertEquals(args[4], "12");
    }

    /**
     * Check multiple name for one UNIQUE command instance.
     */
    @Test
    public void testMultipleCommandsForOneHandler() {
        ClientHandler value = CommandsCollection.getMatchingCommand("create computer").getValue();
        ClientHandler value2 = CommandsCollection.getMatchingCommand("create new computer").getValue();
        ClientHandler value3 = CommandsCollection.getMatchingCommand("new computer").getValue();

        assertSame(value, value2);
        assertSame(value, value3);
    }

    /**
     * Check return null if no match.
     */
    @Test
    public void testReturnNullIfNoMatch() {
        Entry<String, ClientHandler> entry = CommandsCollection.getMatchingCommand("");
        Entry<String, ClientHandler> entry2 = CommandsCollection.getMatchingCommand("notACommand");
        Entry<String, ClientHandler> entry3 = CommandsCollection.getMatchingCommand(null);

        assertNull(entry);
        assertNull(entry2);
        assertNull(entry3);
    }
}
