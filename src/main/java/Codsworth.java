import java.util.Scanner;

public class Codsworth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strTasks = new String[100];
        int i = 0;
        boolean isBye = false;

        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello, I'm Codsworth");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while(!isBye) {
            String strInput = sc.nextLine();
            switch (strInput) {
                case "list":
                    System.out.println("    ____________________________________________________________");
                    for (int j = 0; j < i; j++) {
                        System.out.println("    " + (j + 1) + ". " + strTasks[j]);
                    }
                    System.out.println("    ____________________________________________________________");
                    break;
                case "bye":
                    isBye = true;
                    break;
                default:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    added: " + strInput);
                    System.out.println("    ____________________________________________________________");
                    strTasks[i] = strInput;
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
