package codsworth.codsworthexceptions;

/**
 * Thrown when invalid command is given to Codsworth
 */
public class CodsworthInvalidCommandException extends RuntimeException {
    @Override
    public String toString() {
        return "________________________________________________________"
                + "\nPlease input a valid command"
                + "\nCommands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\nNote that you have to type bye in order to save your list"
                + "\n________________________________________________________";
    }
}
