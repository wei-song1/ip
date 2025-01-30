package Codsworth.CodsworthExceptions;

public class CodsworthInvalidCommandException extends RuntimeException {
    @Override
    public String toString() {
        return "    ____________________________________________________________"
                + "\n    Please input a valid command"
                + "\n    Commands: mark, unmark, delete, todo, deadline, event, reset, bye"
                + "\n    Note that you have to type bye in order to save your list"
                + "\n    ____________________________________________________________";
    }
}
