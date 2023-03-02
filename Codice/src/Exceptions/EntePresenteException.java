package Exceptions;

public class EntePresenteException extends Exception {
    String message = "Organizzatore già presente";
    public String getMessage(){
        return message;
    }
}
