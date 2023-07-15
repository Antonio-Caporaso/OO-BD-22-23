package View.Controller;

import Exceptions.BlankFieldException;

public interface FormChecker {
    void checkFieldsAreBlank() throws BlankFieldException;
}
