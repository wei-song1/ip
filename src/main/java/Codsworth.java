import java.util.Scanner;

public class Codsworth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strTasks = new String[101];
        boolean[] isTasksCompleted = new boolean[101];
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
                        if (isTasksCompleted[j]) {
                            System.out.println("    " + j + ". [X] " + strTasks[j]);
                        } else {
                            System.out.println("    " + j + ". [ ] " + strTasks[j]);
                        }
                    }
                    System.out.println("    ____________________________________________________________");
                    break;

                case "mark" :
                    int intMarked = Integer.parseInt(strInput.split(" ")[1]);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + intMarked + ". [X] " + strTasks[intMarked]);
                    System.out.println("    ____________________________________________________________");
                    isTasksCompleted[intMarked] = true;
                    break;

                case "unmark" :
                    int inUnmarked = Integer.parseInt(strInput.split(" ")[1]);
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("    " + inUnmarked + ". [ ] " + strTasks[inUnmarked]);
                    System.out.println("    ____________________________________________________________");
                    isTasksCompleted[inUnmarked] = false;
                    break;

                case "bye":
                    isBye = true;
                    break;

                default:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    added: " + strInput);
                    System.out.println("    ____________________________________________________________");
                    strTasks[i] = strInput;
                    isTasksCompleted[i] = false;
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
