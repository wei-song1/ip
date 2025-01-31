package codsworth.codsworthexceptions;

/**
 * Thrown when there are missing inputs given to Codsworth
 */
public class CodsworthMissingInputException extends RuntimeException {
    @Override
    public String toString() {
        return "Missing input. Please add an input after the command";
    }
}
