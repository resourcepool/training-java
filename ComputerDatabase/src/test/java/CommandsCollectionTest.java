import static org.junit.Assert.assertEquals;

import org.junit.Test;

import client.CommandsCollection;

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

}
