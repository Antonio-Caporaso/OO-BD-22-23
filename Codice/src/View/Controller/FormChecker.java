package View.Controller;

import Exceptions.BlankFieldException;

public interface FormChecker {
    public void checkFieldsAreBlank() throws BlankFieldException;
}
