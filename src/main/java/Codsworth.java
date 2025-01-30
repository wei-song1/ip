import CodsworthExceptions.*;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Codsworth {
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static File f;
    private static FileWriter fileWriter;

    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma");

    private static void modifyTask(int input, String operation)
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

    private static void modifyTaskWithoutPrinting(int input)
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

    private static void createTask(String input, String operation) {
        try {
            Task temp = null;

            // ToDo
            switch (operation) {
                case "todo" -> temp = new ToDo(input);


                // Deadline
                case "deadline" -> {
                    String strTaskName = input.split(" /by ")[0].replaceFirst("deadline ", "");
                    String strDate = input.split(" /by ")[1];
                    String strFormattedDate = formatCorrectDate(strDate);
                    temp = new Deadline(strTaskName, strFormattedDate);

                    // Event
                }
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

    private static void createTaskWithoutPrinting(String input, String operation) {
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

    private static void saveTaskList() {
        try {
            fileWriter = new FileWriter("codsworth.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int j = 0; j < taskList.size(); j++) {
            String operation = taskList.get(j).getTaskType();
            String task = taskList.get(j).getDescription();
            boolean isDone = taskList.get(j).getIsDone();
            try {
                fileWriter.write(isDone + "/spacer/" + operation + "/spacer/" + task + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initialiseTaskList() {
        try {
            f = new File("codsworth.txt");
            fileWriter = new FileWriter("codsworth.txt", true);
            if (!f.createNewFile()) {
                Scanner s = new Scanner(f);
                int i = 1;
                while (s.hasNextLine()) {
                    String[] temp = s.nextLine().split("/spacer/");
                    String isDone = temp[0];
                    String operation = temp[1];
                    String input = temp[2];
                    createTaskWithoutPrinting(input, operation);
                    if (isDone.equals("true")) {
                        modifyTaskWithoutPrinting(i);
                    }
                    i++;
                }
                s.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (CodsworthWrongFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetTaskList() {
        taskList.clear();
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean isBye = false;
        initialiseTaskList();

        System.out.println(Ui.getIntro());

        while(!isBye) {
            String strInput = sc.nextLine().trim();
            String strCommand = strInput.split(" ")[0];
            String strRest = strInput.replaceFirst(strCommand + " ", "");

            switch (strCommand) {
            case "list":
                System.out.println(Ui.getTaskList(taskList));
                break;

            case "mark":
            case "unmark":
            case "delete":
                try {
                    int taskId = Integer.parseInt(strRest);
                    modifyTask(taskId, strCommand);
                } catch (CodsworthWrongFormatException | CodsworthOutOfBoundsException e) {
                    System.out.println(e);
                }
                break;

            case "todo":
            case "deadline":
            case "event":
                try {
                    if (strInput.split(" ").length < 2) {
                        throw new CodsworthMissingInputException();
                    }
                    createTask(strRest, strCommand);
                } catch (CodsworthMissingInputException e) {
                    System.out.println(e);
                } catch (CodsworthInvalidDateException e) {
                    System.out.println(e);
                }
                break;

            case "bye":
                isBye = true;
                saveTaskList();
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                }
                break;

            case "reset":
                resetTaskList();
                break;

            case "test":
                System.out.println(Ui.getModifiedTaskString("mark", taskList.get(0), 1, 10));
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

        System.out.println(Ui.getOutro());
        sc.close();
    }
}
