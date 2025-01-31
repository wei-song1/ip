package codsworth.task;

import codsworth.codsworthexceptions.CodsworthInvalidDateException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    /**
     * Tests whether <code>'-', '/', '.', ':'</code> is accepted, if the invalid date exception is thrown for dates out of range, and
     * missing inputs
     */
    @Test
    public void testDateFormat() {
        // Checks if symbols are accepted as expected
        assertEquals("2000-01-01", TaskList.formatCorrectDate("01-01-2000"));
        assertEquals("2000-01-01", TaskList.formatCorrectDate("01/01/2000"));
        assertEquals("2000-01-01", TaskList.formatCorrectDate("01.01.2000"));
        assertEquals("2000-01-01", TaskList.formatCorrectDate("01:01:2000"));

        assertEquals("2000-01-01", TaskList.formatCorrectDate("2000-01-01"));
        assertEquals("2000-01-01", TaskList.formatCorrectDate("2000/01/01"));
        assertEquals("2000-01-01", TaskList.formatCorrectDate("2000.01.01"));
        assertEquals("2000-01-01", TaskList.formatCorrectDate("2000:01:01"));

        // Checks if it's invalid date and throws as expected or not
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("00-01-2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("32-01-2000"));

        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-00-2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("00-13-2000"));

        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-0"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-999"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-10000"));

        // Checks for garbage input
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate(""));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate(null));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("a"));
    }

    /**
     * Tests whether <code>'-', '/', '.', ':'</code> is accepted, if the invalid date exception is thrown for dates and timings out of range, and
     * missing inputs
     */
    @Test
    public void testDateTimeFormat() {
        // Checks if symbols are accepted as expected
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("01-01-2000 1234"));
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("01/01/2000 1234"));
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("01.01.2000 1234"));
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("01:01:2000 1234"));

        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("2000-01-01 1234"));
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("2000/01/01 1234"));
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("2000.01.01 1234"));
        assertEquals("2000-01-01T12:34", TaskList.formatCorrectDate("2000:01:01 1234"));

        // Checks if it's invalid date and throws as expected or not
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("00-01-2000 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("32-01-2000 1234"));

        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-00-2000 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("00-13-2000 1234"));

        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-0 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-999 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-10000 1234"));

        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 0"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 00"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 000"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 00000"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 a"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 abcd"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 012d"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 2400"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2000 9999"));

        // Checks for garbage input
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate(""));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate(null));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("a"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("01-01-2001 abcd"));
        assertThrows(CodsworthInvalidDateException.class, () -> TaskList.formatCorrectDate("ab-cd-efgh 0123"));
    }

    /**
     * Tests whether the output is correct for a given operation input and is as intended
     */
    @Test
    public void testAddTaskPrint() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        TaskList taskList = new TaskList();
        TaskList.addTaskAndPrint("abc", "todo");

        assertEquals("________________________________________________________\n" +
                "Got it, I've added this task:\n" +
                "[T][ ] abc\n" +
                "You have 1 task(s) left.\n" +
                "________________________________________________________\n", outContent.toString());

        TaskList.setEmpty();
        outContent.reset();
        TaskList.addTaskAndPrint("abc /by 01-01-2001", "deadline");

        assertEquals("________________________________________________________\n" +
                "Got it, I've added this task:\n" +
                "[D][ ] abc (by: 01 Jan 2001)\n" +
                "You have 1 task(s) left.\n" +
                "________________________________________________________\n", outContent.toString());

        TaskList.setEmpty();
        outContent.reset();
        TaskList.addTaskAndPrint("abc /by 01-01-2001 1800", "deadline");

        assertEquals("________________________________________________________\n" +
                "Got it, I've added this task:\n" +
                "[D][ ] abc (by: 01 Jan 2001 06:00pm)\n" +
                "You have 1 task(s) left.\n" +
                "________________________________________________________\n", outContent.toString());

        TaskList.setEmpty();
        outContent.reset();
        TaskList.addTaskAndPrint("abc /from 01-01-2001 /to 02-02-2002 2000", "event");

        assertEquals("________________________________________________________\n" +
                "Got it, I've added this task:\n" +
                "[E][ ] abc (from: 01 Jan 2001 to: 02 Feb 2002 08:00pm)\n" +
                "You have 1 task(s) left.\n" +
                "________________________________________________________\n", outContent.toString());

        TaskList.setEmpty();
        outContent.reset();
        TaskList.addTaskAndPrint("abc /from 01-01-2001 1000 /to 02-02-2002", "event");

        assertEquals("________________________________________________________\n" +
                "Got it, I've added this task:\n" +
                "[E][ ] abc (from: 01 Jan 2001 10:00am to: 02 Feb 2002)\n" +
                "You have 1 task(s) left.\n" +
                "________________________________________________________\n", outContent.toString());

        TaskList.setEmpty();
        outContent.reset();
        TaskList.addTaskAndPrint("abc /from 01-01-2001 1000 /to 02-02-2002 2000", "event");

        assertEquals("________________________________________________________\n" +
                "Got it, I've added this task:\n" +
                "[E][ ] abc (from: 01 Jan 2001 10:00am to: 02 Feb 2002 08:00pm)\n" +
                "You have 1 task(s) left.\n" +
                "________________________________________________________\n", outContent.toString());

        TaskList.setEmpty();
        outContent.reset();

        System.setOut(originalOut);
    }
}
