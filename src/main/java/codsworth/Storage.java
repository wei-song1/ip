package codsworth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import codsworth.codsworthexceptions.CodsworthWrongFormatException;
import codsworth.task.TaskList;

/**
 * Stores file, fileWriter and filePath details to save and load information
 */
public class Storage {
    private static String filePath;
    private static TaskList taskList;
    private static File f;
    private static FileWriter fileWriter;

    /**
     * Initalises storage based on the file path provided
     *
     * @param filePath Path to file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.taskList = new TaskList();
        this.f = null;
        this.fileWriter = null;
    }

    /**
     * Initalises Codsworth program and loads the task list before returning the initialised task list. Creates a new
     * file and task list if there was none prior
     *
     * @return Initalised task list
     */
    public static TaskList initialiseAndLoadTaskList() {
        try {
            f = new File(filePath);
            fileWriter = new FileWriter(filePath, true);
            if (!f.createNewFile()) {
                Scanner s = new Scanner(f);
                int i = 1;
                while (s.hasNextLine()) {
                    String[] temp = s.nextLine().split("/spacer/");
                    String isDone = temp[0];
                    String operation = temp[1];
                    String input = temp[2];
                    taskList.addTaskWithoutPrinting(input, operation);
                    if (isDone.equals("true")) {
                        taskList.modifyTaskWithoutPrinting(i);
                    }
                    i++;
                }
                s.close();
            }
        } catch (IOException e) {
            System.out.println("DEBUGGING PURPOSE ONLY: Unable to initialise task list");
        } catch (CodsworthWrongFormatException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }

    /**
     * Saves task list into file provided in the file path
     */
    public static void saveTaskList() {
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int j = 0; j < taskList.getTaskListSize(); j++) {
            try {
                fileWriter.write(taskList.getTaskForStorage(j));
            } catch (IOException e) {
                System.out.println("DEBUGGING PURPOSE ONLY: Unable to close file.");
            }
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("DEBUGGING PURPOSE ONLY: Unable to close file.");
        }
    }

    /**
     * Resets task list and resets the file
     */
    public static void resetTaskList() {
        taskList.setEmpty();
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
