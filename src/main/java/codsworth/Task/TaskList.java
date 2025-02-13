package codsworth.task;

import java.util.ArrayList;

import codsworth.codsworthexceptions.CodsworthInvalidDateException;
import codsworth.codsworthexceptions.CodsworthMissingInputException;
import codsworth.codsworthexceptions.CodsworthOutOfBoundsException;
import codsworth.ui.UiString;

/**
 * Represents ArrayList of Task in a TaskList class and encapsulates all it's methods into the class.
 */
public class TaskList {
    private ArrayList<Task> taskList;
    private final String ARGUMENT_SEPERATOR = "/spacer/";

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public String getTaskForStorage(int input) {
        String operation = taskList.get(input).getTaskType();
        String task = taskList.get(input).getDescription();
        boolean isDone = taskList.get(input).getIsDone();
        return isDone + ARGUMENT_SEPERATOR + operation + ARGUMENT_SEPERATOR + task + "\n";
    }

    public String getListAsString() {
        return UiString.getTaskList(taskList);
    }

    public void setEmpty() {
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
    public String formatCorrectDate(String input) throws CodsworthInvalidDateException {
        if (input == null || input.isEmpty() || input.matches(".*[a-zA-Z]+.*")) {
            throw new CodsworthInvalidDateException();
        }

        // Should only accept 1 date seperator, not multiple
        int dotCount = input.contains(".") ? 1 : 0;
        int slashCount = input.contains("/") ? 1 : 0;
        int colonCount = input.contains(":") ? 1 : 0;
        int dashCount = input.contains("-") ? 1 : 0;
        int seperatorCount = dotCount + slashCount + colonCount + dashCount;

        if (seperatorCount != 1) {
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

        if (day > 31 || day < 1 || month > 12 || month < 1 || year > 9999 || year < 1000) {
            throw new CodsworthInvalidDateException();
        }

        output = String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        // If there is time provided as well
        if (inputs.length >= 2) {
            if (inputs[1].length() != 4 || inputs[1].matches(".*[a-zA-Z]+.*")) {
                throw new CodsworthInvalidDateException();
            }

            int hours = Integer.parseInt(inputs[1].substring(0, 2));
            int minutes = Integer.parseInt(inputs[1].substring(2, 4));

            if (hours > 23 || hours < 0 || minutes > 59 || minutes < 0) {
                throw new CodsworthInvalidDateException();
            }

            output = output + "T" + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
        }

        return output;
    }

    /**
     * Handles the task creation based on the operation and input.
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     * @return A Task object based on the operation.
     * @throws CodsworthInvalidDateException If the date format is invalid.
     * @throws CodsworthMissingInputException If the input is missing required fields.
     */
    private Task createTaskFromInput(String input, String operation) throws CodsworthInvalidDateException, CodsworthMissingInputException {
        assert operation.equals("todo") | operation.equals("deadline") | operation.equals("event")
                : "Invalid task type";

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

        return temp;
    }

    /**
     * Used by storage to load task list. Adds the correct task to the task list based on the operation given
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     */
    public void addTaskWithoutPrinting(String input, String operation) {
        try {
            Task temp = createTaskFromInput(input, operation);
            taskList.add(temp);
        } catch (CodsworthMissingInputException e) {
            throw new CodsworthMissingInputException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        }
    }

    /**
     * Adds the correct task to the task list based on the operation given and outputs the corresponding line
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     */
    public String addTaskAndGetString(String input, String operation) {
        try {
            Task temp = createTaskFromInput(input, operation);
            taskList.add(temp);
            return UiString.getNewTask(temp, taskList.size());
        } catch (CodsworthMissingInputException e) {
            throw new CodsworthMissingInputException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        }
    }


    /**
     * Used by storage to load task list. Marks the task in the task list based on the index given as completed. Delete
     * not included since loading task list wouldn't have to delete any tasks.
     *
     * @param input Index of the task to be marked as completed.
     */
    public void modifyTaskWithoutPrinting(int input) throws CodsworthOutOfBoundsException {
        int intMarked = input - 1;
        if (intMarked < 0 || intMarked >= taskList.size()) {
            throw new CodsworthOutOfBoundsException();
        }
        taskList.get(intMarked).setDoneOrUndone("mark");
    }

    /**
     * Modifies the task in the task list based on the operation given and outputs the corresponding line
     *
     * @param input Task name with its optional dates.
     * @param operation Operation currently being added.
     */
    public String modifyTaskAndGetString(int input, String operation) throws CodsworthOutOfBoundsException {
        int intMarked = input - 1;
        if (intMarked < 0 || intMarked >= taskList.size()) {
            throw new CodsworthOutOfBoundsException();
        }
        if (operation.equals("delete")) {
            taskList.remove(intMarked);
            return UiString.getDeletedTaskString();
        } else {
            taskList.get(intMarked).setDoneOrUndone(operation);
            return UiString.getModifiedTaskString(operation,
                    taskList.get(intMarked), intMarked + 1, taskList.size());
        }
    }

    /**
     * Searches task list for matching string and prints out if any
     *
     * @param input String to be searched
     */
    public String searchTaskAndGetString(String input) {
        ArrayList<String> matchingList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).toString().contains(input)) {
                matchingList.add((i + 1) + ". " + taskList.get(i).toString());
            }
        }
        return UiString.getMatchingString(matchingList);
    }
}
