package codsworth.codsworthexceptions;

/**
 * Thrown when input given to Codsworth is invalid or in the wrong format
 */
public class CodsworthWrongFormatException extends Exception {
    @Override
    public String toString() {
        return "Missing index or index must be an integer"
                + "\nFormat: mark [integer]"
                + "\nExample: mark 1";
    }
}
