package codsworth.codsworthexceptions;

import codsworth.Ui.UiString;

/**
 * Thrown when invalid date is given to Codsworth
 */
public class CodsworthInvalidDateException extends RuntimeException {
    @Override
    public String toString() {
        return UiString.getInvalidDateMessage();
    }
}
