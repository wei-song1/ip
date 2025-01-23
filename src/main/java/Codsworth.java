import java.util.Scanner;

public class Codsworth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[101];
        int i = 1;
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
                        System.out.println(tasks[j].getDescription());
                    }
                    System.out.println("    ____________________________________________________________");
                    break;

                case "mark" :
                    int intMarked = Integer.parseInt(strInput.split(" ")[1]);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Nice! I've marked this task as done:");
                    tasks[intMarked].setDone();
                    System.out.println("    " + tasks[intMarked].getDescription());
                    System.out.println("    ____________________________________________________________");
                    break;

                case "unmark" :
                    int intUnmarked = Integer.parseInt(strInput.split(" ")[1]);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    OK, I've marked this task as not done yet:");
                    tasks[intUnmarked].setUndone();
                    System.out.println("    " + tasks[intUnmarked].getDescription());
                    System.out.println("    ____________________________________________________________");
                    break;

                case "bye":
                    isBye = true;
                    break;

                default:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    added: " + strInput);
                    System.out.println("    ____________________________________________________________");
                    Task temp = new Task(strInput);
                    tasks[i] = temp;
                    i++;
                    break;
            }
        }

        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
        sc.close();
    }
}
