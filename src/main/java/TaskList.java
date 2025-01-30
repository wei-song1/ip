import CodsworthExceptions.*;
import java.util.ArrayList;

public class TaskList {
    private static ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<Task>();
    }

    protected static int getTaskListSize() {
        return taskList.size();
    }

    protected static String getTaskForStorage(int input) {
        String operation = taskList.get(input).getTaskType();
        String task = taskList.get(input).getDescription();
        boolean isDone = taskList.get(input).getIsDone();
        return isDone + "/spacer/" + operation + "/spacer/" + task + "\n";
    }

    protected static void getList() {
        System.out.println(Ui.getTaskList(taskList));
    }

    protected static void setEmpty() {
        taskList.clear();
    }

    public static String formatCorrectDate(String input) throws CodsworthInvalidDateException {
        String output = null;
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
            int hours = Integer.parseInt(inputs[1].substring(0, 2));
            int minutes = Integer.parseInt(inputs[1].substring(2, 4));

            if (hours > 24 || hours < 0) {
                throw new CodsworthInvalidDateException();
            }
            if (minutes > 59 || minutes < 0) {
                throw new CodsworthInvalidDateException();
            }

            output = output + "T" + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
        }

        return output;
    }

    protected static void addTaskAndPrint(String input, String operation) {
        try {
            Task temp = null;

            switch (operation) {
                // ToDo
                case "todo" -> temp = new ToDo(input);

                // Deadline
                case "deadline" -> {
                    String strTaskName = input.split(" /by ")[0].replaceFirst("deadline ", "");
                    String strDate = input.split(" /by ")[1];
                    String strFormattedDate = formatCorrectDate(strDate);
                    temp = new Deadline(strTaskName, strFormattedDate);
                }

                // Event
                case "event" -> {
                    String strTaskName = input.split(" /")[0].replaceFirst("event ", "");
                    String strFromDate = input.split(" /")[1].replaceFirst("from ", "");
                    String strToDate = input.split(" /")[2].replaceFirst("to ", "");
                    String strFormattedFromDate = formatCorrectDate(strFromDate);
                    String strFormattedToDate = formatCorrectDate(strToDate);
                    temp = new Event(strTaskName, strFormattedFromDate, strFormattedToDate);
                }
            }

            taskList.add(temp);
            System.out.println(Ui.getNewTask(temp, taskList.size()));

            // Exception
        } catch (CodsworthMissingInputException e) {
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        } catch (NullPointerException e) {
            throw new CodsworthInvalidDateException();
        }
    }

    protected static void addTaskWithoutPrinting(String input, String operation) {
        try {
            Task temp = null;
            switch (operation) {
                case "todo" -> temp = new ToDo(input);

                // Deadline
                case "deadline" -> {
                    String strTaskName = input.split(" /by ")[0].replaceFirst("deadline ", "");
                    String strDate = input.split(" /by ")[1];
                    temp = new Deadline(strTaskName, strDate);
                }

                // Event
                case "event" -> {
                    String strTaskName = input.split(" /")[0].replaceFirst("event ", "");
                    String fromDate = input.split(" /")[1].replaceFirst("from ", "");
                    String toDate = input.split(" /")[2].replaceFirst("to ", "");
                    temp = new Event(strTaskName, fromDate, toDate);
                }
            }

            taskList.add(temp);

            // Exception
        } catch (CodsworthMissingInputException e) {
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        }
    }

    protected static void modifyTaskAndPrint(int input, String operation)
            throws CodsworthWrongFormatException, CodsworthOutOfBoundsException {
        try {
            int intMarked = input - 1;
            if (intMarked < 0 || intMarked >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            if (operation.equals("delete")) {
                System.out.println(Ui.getModifiedTaskString(operation, taskList.get(intMarked), intMarked + 1, taskList.size() - 1));
                taskList.remove(intMarked);
            } else {
                taskList.get(intMarked).setDoneOrUndone(operation);
                System.out.println(Ui.getModifiedTaskString(operation, taskList.get(intMarked), intMarked + 1, taskList.size()));
            }

            // Exceptions
        } catch (IndexOutOfBoundsException exception) {
            throw new CodsworthOutOfBoundsException();
        } catch (NumberFormatException exception) {
            throw new CodsworthWrongFormatException();
        }
    }

    protected static void modifyTaskWithoutPrinting(int input)
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
}
