package codsworth;
import codsworth.CodsworthExceptions.CodsworthInvalidCommandException;
import codsworth.Task.TaskList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void testParse() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        Parser parser = new Parser(new TaskList(), new Storage(""));

        parser.parse("list");
        assertEquals("________________________________________________________\n" +
                "There are no tasks available\n" +
                "________________________________________________________\n", outContent.toString());
        outContent.reset();

        parser.parse("a");
        assertEquals("________________________________________________________"
                + "\nPlease input a valid command"
                + "\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\nNote that you have to type bye in order to save your list"
                + "\n________________________________________________________\n", outContent.toString());
        outContent.reset();

        parser.parse("");
        assertEquals("________________________________________________________"
                + "\nPlease input a valid command"
                + "\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\nNote that you have to type bye in order to save your list"
                + "\n________________________________________________________\n", outContent.toString());
        outContent.reset();

        parser.parse("ddjakdnsjk");
        assertEquals("________________________________________________________"
                + "\nPlease input a valid command"
                + "\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\nNote that you have to type bye in order to save your list"
                + "\n________________________________________________________\n", outContent.toString());
        outContent.reset();

        parser.parse("1");
        assertEquals("________________________________________________________"
                + "\nPlease input a valid command"
                + "\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\nNote that you have to type bye in order to save your list"
                + "\n________________________________________________________\n", outContent.toString());
        outContent.reset();

        parser.parse("-");
        assertEquals("________________________________________________________"
                + "\nPlease input a valid command"
                + "\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\nNote that you have to type bye in order to save your list"
                + "\n________________________________________________________\n", outContent.toString());
        outContent.reset();
        
        assertThrows(CodsworthInvalidCommandException.class, () -> {parser.parse(null);});

        System.setOut(originalOut);
    }
}
