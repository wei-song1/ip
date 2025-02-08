package codsworth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import codsworth.codsworthexceptions.CodsworthWrongFormatException;
import codsworth.task.TaskList;

/**
 * Handles file storage, loading and saving tasks to a file.
 */
public class Storage {
    private final String filePath;
    private final TaskList taskList;
    private final File file;

    /**
     * Initializes storage with the file path provided.
     *
     * @param filePath Path to the file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.taskList = new TaskList();
        this.file = new File(filePath);
    }

    /**
     * Initializes Codsworth program and loads the task list.
     * Creates a new file and task list if there is none.
     *
     * @return Initialized task list.
     */
    public TaskList initialiseAndLoadTaskList() {
        if (file.exists()) {
            loadTaskListFromFile();
        } else {
            createNewFile();
        }
        return taskList;
    }

    /**
     * Loads the task list from the file.
     */
    private void loadTaskListFromFile() {
        try (Scanner scanner = new Scanner(file)) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("/spacer/");
                String isDone = parts[0];
                String operation = parts[1];
                String input = parts[2];
                taskList.addTaskWithoutPrinting(input, operation);
                if (isDone.equals("true")) {
                    taskList.modifyTaskWithoutPrinting(i);
                }
                i++;
            }
        } catch (IOException | CodsworthWrongFormatException e) {
            System.err.println("Error while loading task list: " + e.getMessage());
        }
    }

    /**
     * Saves the task list to the file.
     */
    public void saveTaskList() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (int j = 0; j < taskList.getTaskListSize(); j++) {
                fileWriter.write(taskList.getTaskForStorage(j));
            }
        } catch (IOException e) {
            System.err.println("Error while saving task list: " + e.getMessage());
        }
    }

    /**
     * Resets the task list and deletes the file.
     */
    public void resetTaskList() {
        taskList.setEmpty();
        if (file.exists()) {
            file.delete();
        }
        createNewFile();
    }

    /**
     * Creates a new file if it doesn't exist.
     */
    private void createNewFile() {
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error while creating file: " + e.getMessage());
        }
    }
}
