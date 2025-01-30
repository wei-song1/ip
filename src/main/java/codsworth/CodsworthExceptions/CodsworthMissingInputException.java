package codsworth.CodsworthExceptions;

public class CodsworthMissingInputException extends RuntimeException {
    @Override
    public String toString() {
        return "Missing input. Please add an input after the command";
    }
}
