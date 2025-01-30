import CodsworthExceptions.*;

import java.util.Scanner;

public class Codsworth {
    private static final Storage storage = new Storage("codsworth.txt");
    private static final TaskList taskList = storage.initialiseAndLoadTaskList();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isBye = false;

        while(!isBye) {
            String strInput = sc.nextLine().trim();
            String strCommand = strInput.split(" ")[0];
            String strRest = strInput.replaceFirst(strCommand + " ", "");

            switch (strCommand) {
                case "list":
                    taskList.getList();
                    break;

                case "mark":
                case "unmark":
                case "delete":
                    try {
                        int taskId = Integer.parseInt(strRest);
                        TaskList.modifyTaskAndPrint(taskId, strCommand);
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
                        TaskList.addTaskAndPrint(strRest, strCommand);
                    } catch (CodsworthMissingInputException e) {
                        System.out.println(e);
                    } catch (CodsworthInvalidDateException e) {
                        System.out.println(e);
                    }
                    break;

                case "bye":
                    isBye = true;
                    storage.saveTaskList();
                    break;

                case "reset":
                    storage.resetTaskList();
                    break;

                case "test":
//                    System.out.println(Ui.getModifiedTaskString("mark", taskList.get(0), 1, 10));
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

