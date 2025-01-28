package CodsworthExceptions;

public class CodsworthInvalidDateException extends RuntimeException {
    @Override
    public String toString() {
        return "You need to input the correct format for the by/from/to date."
                + "\nFormat\ntodo [task name]\ndeadline [task name] /by [deadline]"
                + "\nevent [task name] /from [start] /to [end]";
    }
}
