package codsworth;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import codsworth.task.Task;

/**
 * Contains all the text strings to be displayed for user interface
 */
public class Ui {
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma");

    private static final String LOGO = "|   _____          _                        _   _      |\n"
            + "|  / ____|        | |                      | | | |     |\n"
            + "| | |     ___   __| |_____      _____  _ __| |_| |__   |\n"
            + "| | |    / _ \\ / _` / __\\ \\ /\\ / / _ \\| '__| __| '_ \\  |\n"
            + "| | |___| (_) | (_| \\__ \\\\ V  V / (_) | |  | |_| | | | |\n"
            + "|  \\_____\\___/ \\__,_|___/ \\_/\\_/ \\___/|_|   \\__|_| |_| |";

    private static final String LINE_BREAK = "________________________________________________________";

    public static String getIntro() {
        return LINE_BREAK + "\n" + LOGO + "\n" + LINE_BREAK
                + "\nHello there, my name is Codsworth\nWhat can I do for you?\n" + LINE_BREAK;
    }

    public static String getOutro() {
        return LINE_BREAK + "\n" + LOGO + "\n" + LINE_BREAK
                + "\nThank you for using Codsworth\nHope to see you soon!\n" + LINE_BREAK;
    }

    public static String getTaskList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return LINE_BREAK + "\nThere are no tasks available\n" + LINE_BREAK;
        } else {
            StringBuilder output = new StringBuilder();
            output.append(LINE_BREAK + "\nHere are all the task(s) in your list:\n");
            for (int j = 0; j < taskList.size(); j++) {
                output.append((j + 1) + ". " + taskList.get(j).toString() + "\n");
            }
            output.append(LINE_BREAK);
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
        output.append(LINE_BREAK + "\n");

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

        output.append("\n" + LINE_BREAK);
        return output.toString();
    }

    public static String getNewTask(Task task, int size) {
        return LINE_BREAK + "\nGot it, I've added this task:\n" + task
                + "\nYou have " + size + " task(s) left.\n" + LINE_BREAK;
    }

    public static String getMatchingString(ArrayList<String> matchingList) {
        if (matchingList.isEmpty()) {
            return LINE_BREAK + "\nThere are no matching tasks in your list\n" + LINE_BREAK;
        } else {
            String output = LINE_BREAK + "\nHere are the matching task(s):\n";
            for (String s : matchingList) {
                output += s + "\n";
            }
            output += LINE_BREAK;
            return output;
        }
    }
}
