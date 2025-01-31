package codsworth;
import java.util.Scanner;

import codsworth.codsworthexceptions.CodsworthInvalidCommandException;
import codsworth.task.TaskList;

/**
 * Main class for Codsworth
 */
public class Codsworth {
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    /**
     * Initialises which file Codsworth will use for storing/loading of data
     *
     * @param filePath Path name of the file for reading/writing.
     */
    public Codsworth(String filePath) {
        storage = new Storage(filePath);
        taskList = Storage.initialiseAndLoadTaskList();
        parser = new Parser(taskList, storage);
    }

    /**
     * Starts Codsworth until user exits
     */
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (!parser.isBye()) {
            String strInput = sc.nextLine().trim();
            try {
                parser.parse(strInput);
            } catch (CodsworthInvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }

        sc.close();
    }

    public static void main(String[] args) {
        new Codsworth("codsworth.txt").run();
    }
}
