package codsworth;

import codsworth.codsworthexceptions.CodsworthDuplicateException;
import codsworth.codsworthexceptions.CodsworthInvalidCommandException;
import codsworth.codsworthexceptions.CodsworthInvalidDateException;
import codsworth.codsworthexceptions.CodsworthMissingInputException;
import codsworth.codsworthexceptions.CodsworthOutOfBoundsException;
import codsworth.task.TaskList;
import codsworth.ui.UiString;

/**
 * Stores all the user commands and parsing related functions.
 */
public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private boolean hasError = false;

    /**
     * Initializes the parser to be used for Codsworth.
     *
     * @param taskList Task list to be used.
     * @param storage Storage to be used.
     */
    public Parser(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Reads the input and executes the input that was given.
     *
     * @param input Operation to be executed.
     * @return The result of executing the input as a String.
     */
    public String parseAndGetString(String input) {
        hasError = false;
        if (input == null) {
            hasError = true;
            return UiString.getInvalidCommandMessage();
        }

        String[] inputParts = input.split(" ", 2);
        String strCommand = inputParts[0];
        String strRest = inputParts.length > 1
                ? inputParts[1]
                : "";

        switch (strCommand) {
        case "list":
            return taskList.getListAsString();

        case "mark":
        case "unmark":
        case "delete":
            return modifyTaskAndGetString(strRest, strCommand);

        case "todo":
        case "deadline":
        case "event":
            return addTaskAndGetString(strRest, strCommand);

        case "bye":
            storage.saveTaskList();
            return UiString.getOutro();

        case "reset":
            storage.resetTaskList();
            return UiString.getClearedMessage();

        case "find":
            return taskList.searchTaskAndGetString(strRest);

        case "help":
            return UiString.getHelpString();

        default:
            hasError = true;
            return new CodsworthInvalidCommandException().toString();
        }

    }

    /**
     * Adds a task to the task list and returns the result as a String.
     *
     * @param strRest The input string after the command (task description).
     * @param strCommand The task type command (e.g., todo, deadline, event).
     * @return The result of adding the task as a String.
     */
    private String addTaskAndGetString(String strRest, String strCommand) {
        try {
            if (strRest.isEmpty()) {
                throw new CodsworthMissingInputException();
            }

            String output = taskList.addTaskAndGetString(strRest, strCommand);
            storage.saveTaskList();
            return output;
        } catch (CodsworthMissingInputException | CodsworthInvalidDateException | CodsworthDuplicateException e) {
            hasError = true;
            return e.toString();
        }
    }

    /**
     * Modifies a task (mark, unmark, delete) based on the task ID and command, and returns the result as a String.
     *
     * @param strRest The input string containing the task ID.
     * @param strCommand The modify command (mark, unmark, delete).
     * @return The result of modifying the task as a String.
     */
    private String modifyTaskAndGetString(String strRest, String strCommand) {
        try {
            if (strRest.isEmpty()) {
                throw new CodsworthMissingInputException();
            }

            if (!strRest.matches("\\d+")) {
                throw new CodsworthInvalidCommandException();
            }

            int taskId = Integer.parseInt(strRest);
            String output = taskList.modifyTaskAndGetString(taskId, strCommand);
            storage.saveTaskList();
            return output;
        } catch (CodsworthMissingInputException | CodsworthInvalidCommandException
                 | CodsworthOutOfBoundsException e) {
            hasError = true;
            return e.toString();
        }
    }

    /**
     * Returns the command part of the input string.
     *
     * @param input The full input string.
     * @return The command part of the input (the first word).
     * @throws CodsworthInvalidCommandException If the input is null or invalid.
     */
    public String getCommand(String input) {
        if (hasError) {
            return "error";
        }

        if (input == null) {
            throw new CodsworthInvalidCommandException();
        }

        String[] inputParts = input.split(" ", 2);
        return inputParts[0];
    }
}
