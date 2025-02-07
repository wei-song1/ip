package codsworth;

import codsworth.Ui.UiString;
import codsworth.codsworthexceptions.CodsworthInvalidCommandException;
import codsworth.codsworthexceptions.CodsworthInvalidDateException;
import codsworth.codsworthexceptions.CodsworthMissingInputException;
import codsworth.codsworthexceptions.CodsworthOutOfBoundsException;
import codsworth.codsworthexceptions.CodsworthWrongFormatException;
import codsworth.task.TaskList;

/**
 * Stores all the user commands and parsing related functions
 */
public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private boolean isBye = false;

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

    public boolean isBye() {
        return isBye;
    }

    /**
     * LEGACY TERMINAL CODE
     * Reads the input and executes the input that was given
     *
     * @param input Operation to be executed.
     */
    public void parse(String input) {
        if (input == null) {
            throw new CodsworthInvalidCommandException();
        }
        String[] inputParts = input.split(" ", 2);
        String strCommand = inputParts[0];
        String strRest = inputParts.length > 1
                ? inputParts[1]
                : "";

        switch (strCommand) {
        case "list":
            TaskList.getList();
            break;

        case "mark":
        case "unmark":
        case "delete":
            try {
                int taskId = Integer.parseInt(strRest);
                TaskList.modifyTaskAndPrint(taskId, strCommand);
            } catch (CodsworthWrongFormatException | CodsworthOutOfBoundsException e) {
                System.out.println(e);
            }
            break;

        case "todo":
        case "deadline":
        case "event":
            try {
                if (strRest.isEmpty()) {
                    throw new CodsworthMissingInputException();
                }
                TaskList.addTaskAndPrint(strRest, strCommand);
            } catch (CodsworthMissingInputException | CodsworthInvalidDateException e) {
                System.out.println(e);
            }
            break;

        case "bye":
            isBye = true;
            Storage.saveTaskList();
            break;

        case "reset":
            Storage.resetTaskList();
            break;

        case "find":
            TaskList.searchTaskAndPrint(strRest);
            break;

        default:
            try {
                throw new CodsworthInvalidCommandException();
            } catch (CodsworthInvalidCommandException e) {
                System.out.println(e);
            }
            break;
        }
    }

    /**
     * Reads the input and executes the input that was given
     *
     * @param input Operation to be executed.
     */
    public String parseAndGetString(String input) {
        if (input == null) {
            throw new CodsworthInvalidCommandException();
        }
        String[] inputParts = input.split(" ", 2);
        String strCommand = inputParts[0];
        String strRest = inputParts.length > 1
                ? inputParts[1]
                : "";

        switch (strCommand) {
        case "list":
            return TaskList.getListAsString();

        case "mark":
        case "unmark":
        case "delete":
            try {
                int taskId = Integer.parseInt(strRest);
                return TaskList.modifyTaskAndGetString(taskId, strCommand);
            } catch (CodsworthWrongFormatException | CodsworthOutOfBoundsException e) {
                return e.toString();
            }

        case "todo":
        case "deadline":
        case "event":
            try {
                if (strRest.isEmpty()) {
                    throw new CodsworthMissingInputException();
                }
                return TaskList.addTaskAndGetString(strRest, strCommand);
            } catch (CodsworthMissingInputException | CodsworthInvalidDateException e) {
                return e.toString();
            }

        case "bye":
            Storage.saveTaskList();
            return UiString.getOutro();

        case "reset":
            Storage.resetTaskList();
            return UiString.getClearedMessage();

        case "find":
            return TaskList.searchTaskAndGetString(strRest);

        default:
            try {
                throw new CodsworthInvalidCommandException();
            } catch (CodsworthInvalidCommandException e) {
                return e.toString();
            }
        }

    }
}
