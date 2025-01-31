package codsworth;

import codsworth.CodsworthExceptions.CodsworthInvalidCommandException;
import codsworth.CodsworthExceptions.CodsworthInvalidDateException;
import codsworth.CodsworthExceptions.CodsworthMissingInputException;
import codsworth.CodsworthExceptions.CodsworthOutOfBoundsException;
import codsworth.CodsworthExceptions.CodsworthWrongFormatException;
import codsworth.Task.TaskList;

public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private boolean isBye = false;

    public Parser(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    public boolean isBye() {
        return isBye;
    }

    public void parse(String input) {
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

        case "test":
            TaskList.addTaskAndPrint("abc", "todo");
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
}
