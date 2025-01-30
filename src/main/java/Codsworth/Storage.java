package Codsworth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import Codsworth.CodsworthExceptions.*;
import Codsworth.Task.TaskList;

public class Storage {
    private static String filePath;
    private static TaskList taskList;
    private static File f;
    private static FileWriter fileWriter;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.taskList = new TaskList();
        this.f = null;
        this.fileWriter = null;
    }

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
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (CodsworthWrongFormatException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Ui.getIntro());
        return taskList;
    }

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
                e.printStackTrace();
            }
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        System.out.println(Ui.getOutro());
    }

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
        // write task list cleared message
    }
}
