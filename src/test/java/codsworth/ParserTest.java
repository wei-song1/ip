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
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Parser parser = new Parser(new TaskList(), new Storage(""));

        parser.parse("list");
        assertEquals("There are no tasks available\n", outContent.toString());
        outContent.reset();

        parser.parse("a");
        assertEquals("Please input a valid command\n" +
                "\n" +
                "Commands: mark, unmark, delete, todo, deadline, event, reset, bye\n", outContent.toString());
        outContent.reset();

        parser.parse("");
        assertEquals("Please input a valid command\n" +
                "\n" +
                "Commands: mark, unmark, delete, todo, deadline, event, reset, bye\n", outContent.toString());
        outContent.reset();

        parser.parse("ddjakdnsjk");
        assertEquals("Please input a valid command\n" +
                "\n" +
                "Commands: mark, unmark, delete, todo, deadline, event, reset, bye\n", outContent.toString());
        outContent.reset();

        parser.parse("1");
        assertEquals("Please input a valid command\n" +
                "\n" +
                "Commands: mark, unmark, delete, todo, deadline, event, reset, bye\n", outContent.toString());
        outContent.reset();

        parser.parse("-");
        assertEquals("Please input a valid command\n" +
                "\n" +
                "Commands: mark, unmark, delete, todo, deadline, event, reset, bye\n", outContent.toString());
        outContent.reset();

        assertThrows(CodsworthInvalidCommandException.class, () -> {parser.parse(null);});

        System.setOut(originalOut);
    }
}
