package CodsworthExceptions;

public class CodsworthInvalidCommandException extends RuntimeException {
    @Override
    public String toString() {
        return "    ____________________________________________________________"
            + "\n    Please input a valid command. Commands: mark unmark todo event deadline delete"
            + "\n    ____________________________________________________________";
    }
}
