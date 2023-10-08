package Exceptions;

public class SessionePresenteException extends Throwable {
    private String message = "Sessione già presente";
    public String getMessage(){
        return message;
    }
}
