package codsworth.codsworthexceptions;

/**
 * Thrown when numbers given to Codsworth is out of bounds
 */
public class CodsworthOutOfBoundsException extends RuntimeException {
    @Override
    public String toString() {
        return "Number provided must be within the available lists of task's range."
                + "\nIf there are no tasks available, create a new one first.";
    }
}
