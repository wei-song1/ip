import java.util.Arrays;
import java.util.Scanner;

public class Codsworth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[101];

        int i = 1; // Number of tasks
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
            switch (strCommand) {
                case "list":
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Here are the tasks in your list:");
                    for (int j = 1; j < i; j++) {
                        System.out.println("    " + j + "." + tasks[j].toString());
                    }
                    System.out.println("    ____________________________________________________________");
                    break;

                case "mark":
                    intMarked = Integer.parseInt(strInput.split(" ")[1]);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Nice! I've marked this task as done:");
                    tasks[intMarked].setDone();
                    System.out.println("    " + tasks[intMarked].toString());
                    System.out.println("    ____________________________________________________________");
                    break;

                case "unmark":
                    intMarked = Integer.parseInt(strInput.split(" ")[1]);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    OK, I've marked this task as not done yet:");
                    tasks[intMarked].setUndone();
                    System.out.println("      " + tasks[intMarked].toString());
                    System.out.println("    ____________________________________________________________");
                    break;

                case "todo":
                    strTaskName = strInput.replaceFirst("todo ","");
                    tasks[i] = new ToDo(strTaskName);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + tasks[i].toString());
                    System.out.println("    Now you have " + i + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    i++;
                    break;

                case "deadline":
                    strTaskName = strInput.split(" /by ")[0].replaceFirst("deadline ","");
                    strDate = strInput.split(" /by ")[1];
                    tasks[i] = new Deadline(strTaskName, strDate);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + tasks[i].toString());
                    System.out.println("    Now you have " + i + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    i++;
                    break;

                case "event":
                    strTaskName = strInput.split(" /")[0].replaceFirst("event ","");
                    strDate = strInput.split(" /")[1].replaceFirst("from ","");
                    strEndDate = strInput.split(" /")[2].replaceFirst("to ","");
                    tasks[i] = new Event(strTaskName, strDate, strEndDate);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + tasks[i].toString());
                    System.out.println("    Now you have " + i + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                    i++;
                    break;

                case "bye":
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
