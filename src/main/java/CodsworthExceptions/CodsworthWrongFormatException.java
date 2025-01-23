package CodsworthExceptions;

public class CodsworthWrongFormatException extends Exception {
    @Override
    public String toString() {
        return "Missing index or index must be an integer" +
                "\nFormat: mark [integer]" +
                "\nExample: mark 1";
    }
}
