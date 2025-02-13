package codsworth;
import codsworth.codsworthexceptions.CodsworthInvalidCommandException;
import codsworth.task.TaskList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    /**
     * Tests commands to make sure that it works as intended
     */
    @Test
    public void testParse() {
        Parser parser = new Parser(new TaskList(), new Storage(""));

        assertEquals(parser.parseAndGetString("list"), "There are no tasks available");

        assertEquals(parser.parseAndGetString("a"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString(""), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("dwandsadknjswajknasddwaisd"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("1"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("-"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("/"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("."), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("`"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        // Check mark, unmark, delete
        assertEquals(parser.parseAndGetString("mark dasda"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("mark 0.1"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("mark 1.0"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("unmark dasda"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("unmark 0.1"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("unmark 1.0"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("delete dasda"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("delete 0.1"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");

        assertEquals(parser.parseAndGetString("delete 1.0"), "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye");
    }
}
