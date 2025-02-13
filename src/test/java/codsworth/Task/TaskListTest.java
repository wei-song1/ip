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
        TaskList taskList = new TaskList();

        assertEquals("2000-01-01", taskList.formatCorrectDate("01-01-2000"));
        assertEquals("2000-01-01", taskList.formatCorrectDate("01/01/2000"));
        assertEquals("2000-01-01", taskList.formatCorrectDate("01.01.2000"));
        assertEquals("2000-01-01", taskList.formatCorrectDate("01:01:2000"));

        assertEquals("2000-01-01", taskList.formatCorrectDate("2000-01-01"));
        assertEquals("2000-01-01", taskList.formatCorrectDate("2000/01/01"));
        assertEquals("2000-01-01", taskList.formatCorrectDate("2000.01.01"));
        assertEquals("2000-01-01", taskList.formatCorrectDate("2000:01:01"));

        // Checks if it's invalid date and throws as expected or not
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("00-01-2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("32-01-2000"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-00-2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("00-13-2000"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-0"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-999"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-10000"));

        // Checks for garbage input
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate(""));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate(null));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("a"));
    }

    /**
     * Tests whether <code>'-', '/', '.', ':'</code> is accepted, if the invalid date exception is thrown for dates and timings out of range, and
     * missing inputs
     */
    @Test
    public void testDateTimeFormat() {
        // Checks if symbols are accepted as expected
        TaskList taskList = new TaskList();

        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("01-01-2000 1234"));
        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("01/01/2000 1234"));
        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("01.01.2000 1234"));
        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("01:01:2000 1234"));

        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("2000-01-01 1234"));
        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("2000/01/01 1234"));
        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("2000.01.01 1234"));
        assertEquals("2000-01-01T12:34", taskList.formatCorrectDate("2000:01:01 1234"));

        // Checks if it's invalid date and throws as expected or not
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("00-01-2000 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("32-01-2000 1234"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-00-2000 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("00-13-2000 1234"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-0 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-999 1234"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-10000 1234"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 0"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 00"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 00000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 a"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 abcd"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 012d"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 2400"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2000 9999"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01 01 2000 9999"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01[01[2000 9999"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01]01]2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01a01a2000"));

        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01.01-2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01/01.2000"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01:2000"));

        // Checks for garbage input
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate(""));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate(null));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("a"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("01-01-2001 abcd"));
        assertThrows(CodsworthInvalidDateException.class, () -> taskList.formatCorrectDate("ab-cd-efgh 0123"));
    }

    /**
     * Tests whether the output is correct for a given operation input and is as intended
     */
    @Test
    public void testAddTaskPrint() {
        TaskList taskList = new TaskList();

        assertEquals("Got it, I've added this task:\n"
                + "[T][ ] abc\n"
                + "You have 1 task(s) left.", taskList.addTaskAndGetString("abc", "todo"));

        taskList.setEmpty();
        assertEquals("Got it, I've added this task:\n"
                + "[D][ ] abc (by: 01 Jan 2001)\n"
                + "You have 1 task(s) left.", taskList.addTaskAndGetString("abc /by 01-01-2001", "deadline"));

        taskList.setEmpty();
        assertEquals("Got it, I've added this task:\n"
                + "[D][ ] abc (by: 01 Jan 2001 06:00pm)\n"
                + "You have 1 task(s) left.", taskList.addTaskAndGetString("abc /by 01-01-2001 1800", "deadline"));

        taskList.setEmpty();
        assertEquals("Got it, I've added this task:\n"
                + "[E][ ] abc (from: 01 Jan 2001 to: 02 Feb 2002 08:00pm)\n"
                + "You have 1 task(s) left.", taskList.addTaskAndGetString("abc /from 01-01-2001 /to 02-02-2002 2000", "event"));

        taskList.setEmpty();
        assertEquals("Got it, I've added this task:\n"
                + "[E][ ] abc (from: 01 Jan 2001 10:00am to: 02 Feb 2002)\n"
                + "You have 1 task(s) left.", taskList.addTaskAndGetString("abc /from 01-01-2001 1000 /to 02-02-2002", "event"));

        taskList.setEmpty();
        assertEquals("Got it, I've added this task:\n"
                + "[E][ ] abc (from: 01 Jan 2001 10:00am to: 02 Feb 2002 08:00pm)\n"
                + "You have 1 task(s) left.", taskList.addTaskAndGetString("abc /from 01-01-2001 1000 /to 02-02-2002 2000", "event"));
    }
}
