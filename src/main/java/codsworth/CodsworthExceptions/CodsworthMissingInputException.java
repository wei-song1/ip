package codsworth.codsworthexceptions;

import codsworth.ui.UiString;

/**
 * Thrown when there are missing inputs given to Codsworth
 */
public class CodsworthMissingInputException extends RuntimeException {
    @Override
    public String toString() {
        return UiString.getMissingInputMessage();
    }
}
