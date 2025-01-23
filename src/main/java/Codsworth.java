import java.util.ArrayList;
import java.util.Scanner;

public class Codsworth {
    enum Operation {
        LIST,
        MARK,
        UNMARK,
        DELETE,
        TODO,
        DEADLINE,
        EVENT,
        BYE
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        int intMarked; // Which task is marked to be done/undone

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
            Operation command = Operation.valueOf(strCommand.toUpperCase());
            switch (command) {
                case LIST:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Here are the tasks in your list:");
                    for (int j = 0; j < taskList.size(); j++) {
                        System.out.println("    " + (j + 1) + "." + taskList.get(j).toString());
                    }
                    System.out.println("    ____________________________________________________________");
                    break;

                case MARK:
                    intMarked = Integer.parseInt(strInput.split(" ")[1]) - 1;
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Nice! I've marked this task as done:");
                    taskList.get(intMarked).setDone();
                    System.out.println("    " + taskList.get(intMarked).toString());
                    System.out.println("    ____________________________________________________________");
                    break;

                case UNMARK:
                    intMarked = Integer.parseInt(strInput.split(" ")[1]) - 1;
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    OK, I've marked this task as not done yet:");
                    taskList.get(intMarked).setUndone();
                    System.out.println("    " + taskList.get(intMarked).toString());
                    System.out.println("    ____________________________________________________________");
                    break;

                case DELETE:
                    intMarked = Integer.parseInt(strInput.split(" ")[1]) - 1;
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Noted. I've removed this task:");
                    System.out.println("    " + taskList.get(intMarked).toString());
                    taskList.remove(intMarked);
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    break;

                case TODO:
                    strTaskName = strInput.replaceFirst("todo ","");
                    Task temp = new ToDo(strTaskName);
                    taskList.add(temp);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + temp);
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    break;

                case DEADLINE:
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

                case EVENT:
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

                case BYE:
                    isBye = true;
                    break;

                default:
                    break;
            }
        }

        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
        sc.close();
    }
}
