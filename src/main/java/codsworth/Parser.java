package codsworth;

import codsworth.codsworthexceptions.CodsworthDuplicateException;
import codsworth.codsworthexceptions.CodsworthInvalidCommandException;
import codsworth.codsworthexceptions.CodsworthInvalidDateException;
import codsworth.codsworthexceptions.CodsworthMissingInputException;
import codsworth.codsworthexceptions.CodsworthOutOfBoundsException;
import codsworth.task.TaskList;
import codsworth.ui.UiString;

/**
 * Stores all the user commands and parsing related functions
 */
public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private boolean hasError = false;

    /**
     * Initialises the parser to be used for Codsworth
     *
     * @param taskList Task list to be used.
     * @param storage Storage to be used.
     */
    public Parser(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Reads the input and executes the input that was given
     *
     * @param input Operation to be executed.
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

        case "todo":
        case "deadline":
        case "event":
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
