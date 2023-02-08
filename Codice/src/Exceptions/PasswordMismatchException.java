package Exceptions;

public class PasswordMismatchException extends Exception {
    String message = "Le password non corrispondono";
    @Override
    public String getMessage(){
        return message;
    }
}
