import java.util.Scanner;

public class Codsworth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isBye = false;

        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello, I'm Codsworth");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");
        while(!isBye) {
            String strInput = sc.nextLine();
            if (strInput.equals("bye")) {
                isBye = true;
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    " + strInput);
                System.out.println("    ____________________________________________________________");
            }
        }
        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
        sc.close();
    }
}
