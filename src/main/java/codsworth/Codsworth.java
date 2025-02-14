package codsworth;

import codsworth.task.TaskList;

/**
 * Main class for Codsworth
 */
public class Codsworth {
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;
    private String commandType;

    /**
     * Initialises which file Codsworth will use for storing/loading of data
     *
     * @param filePath Path name of the file for reading/writing.
     */
    public Codsworth(String filePath) {
        assert !filePath.isEmpty() : "File path cannot be empty";
        this.storage = new Storage(filePath);
        this.taskList = storage.initialiseAndLoadTaskList();
        this.parser = new Parser(taskList, storage);
    }

    /**
     * Parses one single input and returns as a string
     *
     * @param input Command with arguments
     * @return Expected output in string form instead of printing into terminal
     */
    public String handleResponse(String input) {
        String strInput = input.trim();
        String output;
        output = parser.parseAndGetString(strInput);
        commandType = parser.getCommand(strInput);
        return output;
    }

    public String getCommandType() {
        return commandType;
    }
}
