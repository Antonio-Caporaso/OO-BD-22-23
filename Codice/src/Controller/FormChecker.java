package Controller;

import Exceptions.BlankFieldException;

public interface FormChecker {
    public void textFieldsAreBlank() throws BlankFieldException;
}
