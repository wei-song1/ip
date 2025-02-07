package codsworth.codsworthexceptions;

import codsworth.Ui.UiString;

/**
 * Thrown when invalid command is given to Codsworth
 */
public class CodsworthInvalidCommandException extends RuntimeException {
    @Override
    public String toString() {
        return UiString.getInvalidCommandMessage();
    }
}
