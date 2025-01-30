package codsworth.CodsworthExceptions;

public class CodsworthInvalidDateException extends RuntimeException {
    @Override
    public String toString() {
        return "You need to input the correct format for the by/from/to date."
                + "\n\nFormat\ntodo [task name]\ndeadline [task name] /by [DATE]"
                + "\nevent [task name] /from [DATE] /to [DATE]"
                + "\n\nDate Format"
                + "\nDD-MM-YYYY or YYYY-MM-DD"
                + "\nDD-MM-YYYY HHMM or YYYYY-MM-DD HHMM"
                + "\nYou can use '-', '/', '.', ':' to separate dates.";
    }
}
