import CodsworthExceptions.CodsworthOutOfBoundsException;
import CodsworthExceptions.CodsworthWrongFormatException;

import java.util.ArrayList;
import java.util.Scanner;

public class Codsworth {
    private static final ArrayList<Task> taskList = new ArrayList<>();
//    enum Operation {
//        LIST,
//        MARK,
//        UNMARK,
//        DELETE,
//        TODO,
//        DEADLINE,
//        EVENT,
//        BYE
//    }
    private static void printTaskList() {
        System.out.println("    ____________________________________________________________");
        System.out.println("    Here are the tasks in your list:");
        for (int j = 0; j < taskList.size(); j++) {
            System.out.println("    " + (j + 1) + "." + taskList.get(j).toString());
        }
        System.out.println("    ____________________________________________________________");
    }

    private static void modifyTask(String input, String operation) throws CodsworthWrongFormatException, CodsworthOutOfBoundsException {
        try {
            int intMarked = Integer.parseInt(input) - 1;
            if (intMarked < 0 || intMarked >= taskList.size()) {
                throw new IndexOutOfBoundsException();
            }

            System.out.println("    ____________________________________________________________");
            if (operation.equals("delete")) { // Delete Command
                System.out.println("    Noted. I've removed this task:");
                System.out.println("    " + taskList.get(intMarked).toString());
                taskList.remove(intMarked);
                System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
            } else {
                if (operation.equals("mark")) { // Mark Command
                    System.out.println("    Nice! I've marked this task as done:");
                    taskList.get(intMarked).setDone();
                } else if (operation.equals("unmark")) { // Unmark Command
                    System.out.println("    OK, I've marked this task as not done yet:");
                    taskList.get(intMarked).setUndone();
                }
                System.out.println("    " + taskList.get(intMarked).toString());
            }
            System.out.println("    ____________________________________________________________");

        } catch (IndexOutOfBoundsException exception) {
            throw new CodsworthOutOfBoundsException();
        } catch (NumberFormatException exception) {
            throw new CodsworthWrongFormatException();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String strTaskName; // Name of task
        String strDate; // Deadline's by date/Event's from date
        String strEndDate; // Event's to date

        boolean isBye = false;

        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello, I'm Codsworth");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while(!isBye) {
            String strInput = sc.nextLine();
            String strCommand = strInput.split(" ")[0];
            String strRest = strInput.replace(strCommand + " ", "");
//            Operation command = Operation.valueOf(strCommand.toUpperCase());

            switch (strCommand) {
                case "list":
                    printTaskList();
                    break;

                case "mark":
                case "unmark":
                case "delete":
                    try {
                        modifyTask(strRest, strCommand);
                    } catch (CodsworthWrongFormatException | CodsworthOutOfBoundsException e) {
                        //noinspection ThrowablePrintedToSystemOut
                        System.out.println(e);
                    }
                    break;

                case "todo":
                    strTaskName = strInput.replaceFirst("todo ","");
                    Task temp = new ToDo(strTaskName);
                    taskList.add(temp);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + temp);
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    break;

                case "deadline":
                    strTaskName = strInput.split(" /by ")[0].replaceFirst("deadline ","");
                    strDate = strInput.split(" /by ")[1];
                    Task temp1 = new Deadline(strTaskName, strDate);
                    taskList.add(temp1);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + temp1);
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    break;

                case "event":
                    strTaskName = strInput.split(" /")[0].replaceFirst("event ","");
                    strDate = strInput.split(" /")[1].replaceFirst("from ","");
                    strEndDate = strInput.split(" /")[2].replaceFirst("to ","");
                    Task temp2 = new Event(strTaskName, strDate, strEndDate);
                    taskList.add(temp2);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + temp2);
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    break;

                case "bye":
                    isBye = true;
                    break;

                default:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Please input a valid command. Commands: mark unmark todo event deadline delete");
                    System.out.println("    ____________________________________________________________");
                    break;
            }
        }

        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
        sc.close();
    }
}
