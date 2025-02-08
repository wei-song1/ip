package codsworth.ui;

import java.util.ArrayList;

import codsworth.task.Task;

/**
 * Contains every string element of the chatbot
 */
public class UiString {
    public static String getIntro() {
        return "Hello there, my name is Codsworth\nWhat can I do for you?";
    }

    public static String getOutro() {
        return "Thank you for using Codsworth\nHope to see you soon!";
    }

    public static String getTaskList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "There are no tasks available";
        } else {
            StringBuilder output = new StringBuilder();
            output.append("Here are all the task(s) in your list:");
            for (int j = 0; j < taskList.size(); j++) {
                output.append("\n" + (j + 1) + ". " + taskList.get(j).toString());
            }
            return output.toString();
        }
    }

    /**
     * Returns string that says whether its completed, uncompleted or deleted based on inputs
     *
     * @param operation "mark", "unmark" or "delete"
     * @param task Task that is being modified
     * @param index Task ID to display which task number is being modified
     * @param size Size of task list to display how many tasks remaining
     * @return Returns string based on the operation being executed
     */
    public static String getModifiedTaskString(String operation, Task task, int index, int size) {
        StringBuilder output = new StringBuilder();
        switch (operation) {
        case "mark":
            output.append("Nice, I've marked this task as completed!\n");
            output.append(index + ". " + task.toString());
            output.append("\nYou have " + size + " task(s) left.");
            break;
        case "unmark":
            output.append("I've unmarked this task as completed.\n");
            output.append(index + ". " + task.toString());
            output.append("\nYou have " + size + " task(s) left.");
            break;
        case "delete":
            output.append("I've deleted this task.\n");
            output.append(index + ". " + task.toString());
            output.append("\nYou have " + size + " task(s) left.");
            break;
        default:
            break;
        }

        return output.toString();
    }

    public static String getNewTask(Task task, int size) {
        return "Got it, I've added this task:\n" + task + "\nYou have " + size + " task(s) left.";
    }

    public static String getClearedMessage() {
        return "Everything has been cleared.";
    }

    public static String getMatchingString(ArrayList<String> matchingList) {
        if (matchingList.isEmpty()) {
            return "There are no matching tasks in your list";
        } else {
            String output = "Here are the matching task(s):";
            for (String s : matchingList) {
                output += "\n" + s;
            }
            return output;
        }
    }

    public static String getDeletedTaskString() {
        return "The task has been deleted.";
    }



    // Exceptions
    public static String getInvalidCommandMessage() {
        return "Please input a valid command"
                + "\n\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye";
    }

    public static String getInvalidDateMessage() {
        return "Please input a valid date"
                + "\n\nHint"
                + "\nDD-MM-YYYY or YYYY-MM-DD and optional timing"
                + "\nOptional timing accepted too: DD-MM-YYYY HHMM"
                + "\nYou can use '-', '/', '.', ':' to separate dates";
    }

    public static String getMissingInputMessage() {
        return "Please include an input after the command"
                + "\n\nHint"
                + "\ntodo [task name]"
                + "\ndeadline [task name] /by [formatted date]"
                + "\nevent [task name] /from [formatted date] /to [formatted date]";
    }

    public static String getOutOfBoundsMessage() {
        return "Please input a valid number"
                + "\nNumber provided must be within the available lists of task's range"
                + "\nIf there are no tasks available, create a new one first";
    }

    public static String getInvalidFormatMessage() {
        return "Please input an integer"
                + "\n\nHint"
                + "\nFormat: mark [integer]"
                + "\nExample: mark 1";
    }
}
