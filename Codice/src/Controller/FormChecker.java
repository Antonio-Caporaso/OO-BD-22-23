package Controller;

import Exceptions.BlankFieldException;

public interface FormChecker {
    public void checkTextFieldsAreBlank() throws BlankFieldException;
}
