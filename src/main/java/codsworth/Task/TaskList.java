package codsworth.task;

import java.util.ArrayList;

import codsworth.Ui;
import codsworth.codsworthexceptions.CodsworthInvalidDateException;
import codsworth.codsworthexceptions.CodsworthMissingInputException;
import codsworth.codsworthexceptions.CodsworthOutOfBoundsException;
import codsworth.codsworthexceptions.CodsworthWrongFormatException;

/**
 * Represents ArrayList of Task in a TaskList class and encapsulates all it's methods into the class.
 */
public class TaskList {
    private static ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public static int getTaskListSize() {
        return taskList.size();
    }

    public static String getTaskForStorage(int input) {
        String operation = taskList.get(input).getTaskType();
        String task = taskList.get(input).getDescription();
        boolean isDone = taskList.get(input).getIsDone();
        return isDone + "/spacer/" + operation + "/spacer/" + task + "\n";
    }

    public static void getList() {
        System.out.println(Ui.getTaskList(taskList));
    }

    public static void setEmpty() {
        taskList.clear();
    }

    /**
     * Returns a properly formatted string for LocalDate/LocalDateTime, as well as checks if the string is
     * properly written to be formatted.
     *
     * @param input Date string.
     * @return Properly inputted string for LocalDate/LocalDateTime.
     * @throws CodsworthInvalidDateException If provided date is null, empty, contains characters or impossible.
     */
    public static String formatCorrectDate(String input) throws CodsworthInvalidDateException {
        if (input == null || input.isEmpty() || input.matches(".*[a-zA-Z]+.*")) {
            throw new CodsworthInvalidDateException();
        }

        String output;
        String[] inputs = input.trim().split(" ");

        String[] dates = null;
        if (input.contains("-")) {
            dates = inputs[0].split("-");
        } else if (input.contains("/")) {
            dates = inputs[0].split("/");
        } else if (input.contains(":")) {
            dates = inputs[0].split(":");
        } else if (input.contains(".")) {
            dates = inputs[0].split("\\.");
        }

        int day;
        int month;
        int year;

        if (dates[0].length() == 2) {
            day = Integer.parseInt(dates[0]);
            month = Integer.parseInt(dates[1]);
            year = Integer.parseInt(dates[2]);
        } else {
            day = Integer.parseInt(dates[2]);
            month = Integer.parseInt(dates[1]);
            year = Integer.parseInt(dates[0]);
        }

        if (day > 31 || day < 1) {
            throw new CodsworthInvalidDateException();
        }
        if (month > 12 || month < 1) {
            throw new CodsworthInvalidDateException();
        }
        if (year > 9999 || year < 1000) {
            throw new CodsworthInvalidDateException();
        }

        output = String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        if (inputs.length >= 2) {
            if (inputs[1].length() != 4 || inputs[1].matches(".*[a-zA-Z]+.*")) {
                throw new CodsworthInvalidDateException();
            }

            int hours = Integer.parseInt(inputs[1].substring(0, 2));
            int minutes = Integer.parseInt(inputs[1].substring(2, 4));

            if (hours > 23 || hours < 0) {
                throw new CodsworthInvalidDateException();
            }
            if (minutes > 59 || minutes < 0) {
                throw new CodsworthInvalidDateException();
            }

            output = output + "T" + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
        }

        return output;
    }

    /**
     * Adds the correct task to the task list based on the operation given and outputs the corresponding line
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     */
    public static void addTaskAndPrint(String input, String operation) {
        try {
            Task temp = null;

            switch (operation) {
            case "todo" -> temp = new ToDo(input);

            case "deadline" -> {
                String strTaskName = input.split(" /by ")[0].replaceFirst("deadline ", "");
                String strDate = input.split(" /by ")[1];
                String strFormattedDate = formatCorrectDate(strDate);
                temp = new Deadline(strTaskName, strFormattedDate);
            }

            case "event" -> {
                String strTaskName = input.split(" /")[0].replaceFirst("event ", "");
                String strFromDate = input.split(" /")[1].replaceFirst("from ", "");
                String strToDate = input.split(" /")[2].replaceFirst("to ", "");
                String strFormattedFromDate = formatCorrectDate(strFromDate);
                String strFormattedToDate = formatCorrectDate(strToDate);
                temp = new Event(strTaskName, strFormattedFromDate, strFormattedToDate);
            }

            default -> { }
            }

            taskList.add(temp);
            System.out.println(Ui.getNewTask(temp, taskList.size()));

        // Exception
        } catch (CodsworthMissingInputException e) {
            throw new CodsworthMissingInputException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        }
    }

    /**
     * Adds the correct task to the task list based on the operation given
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     */
    public static void addTaskWithoutPrinting(String input, String operation) {
        try {
            Task temp = null;
            switch (operation) {
            case "todo" -> temp = new ToDo(input);

            case "deadline" -> {
                String strTaskName = input.split(" /by ")[0].replaceFirst("deadline ", "");
                String strDate = input.split(" /by ")[1];
                temp = new Deadline(strTaskName, strDate);
            }

            case "event" -> {
                String strTaskName = input.split(" /")[0].replaceFirst("event ", "");
                String fromDate = input.split(" /")[1].replaceFirst("from ", "");
                String toDate = input.split(" /")[2].replaceFirst("to ", "");
                temp = new Event(strTaskName, fromDate, toDate);
            }

            default -> { }
            }

            taskList.add(temp);

        // Exception
        } catch (CodsworthMissingInputException e) {
            throw new CodsworthMissingInputException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        }
    }

    /**
     * Modifies the task in the task list based on the operation given and outputs the corresponding line
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     */
    public static void modifyTaskAndPrint(int input, String operation)
            throws CodsworthWrongFormatException, CodsworthOutOfBoundsException {
        try {
            int intMarked = input - 1;
            if (intMarked < 0 || intMarked >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            if (operation.equals("delete")) {
                System.out.println(Ui.getModifiedTaskString(operation,
                        taskList.get(intMarked), intMarked + 1, taskList.size() - 1));
                taskList.remove(intMarked);
            } else {
                taskList.get(intMarked).setDoneOrUndone(operation);
                System.out.println(Ui.getModifiedTaskString(operation,
                        taskList.get(intMarked), intMarked + 1, taskList.size()));
            }

        // Exceptions
        } catch (IndexOutOfBoundsException exception) {
            throw new CodsworthOutOfBoundsException();
        } catch (NumberFormatException exception) {
            throw new CodsworthWrongFormatException();
        }
    }

    /**
     * Marks the task in the task list based on the index given as completed
     *
     * @param input Index of the task to be marked as completed.
     */
    public static void modifyTaskWithoutPrinting(int input)
            throws CodsworthWrongFormatException, CodsworthOutOfBoundsException {

        try {
            int intMarked = input - 1;
            if (intMarked < 0 || intMarked >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            taskList.get(intMarked).setDoneOrUndone("mark");

        // Exceptions
        } catch (IndexOutOfBoundsException exception) {
            throw new CodsworthOutOfBoundsException();
        } catch (NumberFormatException exception) {
            throw new CodsworthWrongFormatException();
        }
    }

    /**
     * Searches task list for matching string and prints out if any
     *
     * @param input String to be searched
     */
    public static void searchTaskAndPrint(String input) {
        ArrayList<String> matchingList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).toString().contains(input)) {
                matchingList.add((i + 1) + ". " + taskList.get(i).toString());
            }
        }
        System.out.println(Ui.getMatchingString(matchingList));
    }
}
