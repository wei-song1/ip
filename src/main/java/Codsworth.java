import CodsworthExceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;

public class Codsworth {
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static File f;
    private static FileWriter fileWriter;

    private static void printTaskList() {
        System.out.println("    ____________________________________________________________");
        System.out.println("    Here are the tasks in your list:");
        for (int j = 0; j < taskList.size(); j++) {
            System.out.println("    " + (j + 1) + "." + taskList.get(j).toString());
        }
        System.out.println("    ____________________________________________________________");
    }

    private static void modifyTask(int input, String operation)
            throws CodsworthWrongFormatException, CodsworthOutOfBoundsException {
        try {
            int intMarked = input - 1;
            if (intMarked < 0 || intMarked >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            System.out.println("    ____________________________________________________________");

            // Delete Command
            if (operation.equals("delete")) {
                System.out.println("    Noted. I've removed this task:");
                System.out.println("    " + taskList.get(intMarked).toString());
                taskList.remove(intMarked);
                System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
            } else {

                // Mark Command
                if (operation.equals("mark")) {
                    System.out.println("    Nice! I've marked this task as done:");
                    taskList.get(intMarked).setDone();

                // Unmark Command
                } else if (operation.equals("unmark")) {
                    System.out.println("    OK, I've marked this task as not done yet:");
                    taskList.get(intMarked).setUndone();
                }
                System.out.println("    " + taskList.get(intMarked).toString());
            }
            System.out.println("    ____________________________________________________________");

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
                    temp = new Deadline(strTaskName, strDate);

                    // Event
                }
                case "event" -> {
                    String strTaskName = input.split(" /")[0].replaceFirst("event ", "");
                    String fromDate = input.split(" /")[1].replaceFirst("from ", "");
                    String toDate = input.split(" /")[2].replaceFirst("to ", "");
                    temp = new Event(strTaskName, fromDate, toDate);
                }
            }

            taskList.add(temp);
            System.out.println("    ____________________________________________________________");
            System.out.println("    Got it. I've added this task:");
            System.out.println("      " + temp);
            System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
            System.out.println("    ____________________________________________________________");

        // Exception
        } catch (CodsworthMissingInputException e) {
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CodsworthInvalidDateException();
        }
    }

    private static void modifyTaskWithoutPrinting(int input)
            throws CodsworthWrongFormatException, CodsworthOutOfBoundsException {
        try {
            int intMarked = input - 1;
            if (intMarked < 0 || intMarked >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            taskList.get(intMarked).setDone();

        // Exceptions
        } catch (IndexOutOfBoundsException exception) {
            throw new CodsworthOutOfBoundsException();
        } catch (NumberFormatException exception) {
            throw new CodsworthWrongFormatException();
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean isBye = false;
        initialiseTaskList();

        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello, I'm Codsworth");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while(!isBye) {
            String strInput = sc.nextLine().trim();
            String strCommand = strInput.split(" ")[0];
            String strRest = strInput.replaceFirst(strCommand + " ", "");

            switch (strCommand) {
            case "list":
                printTaskList();
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

            default:
                try {
                    throw new CodsworthInvalidCommandException();
                } catch (CodsworthInvalidCommandException e) {
                    System.out.println(e);
                }
                break;
            }
        }

        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
        sc.close();
    }
}
