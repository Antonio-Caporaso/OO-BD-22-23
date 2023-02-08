package Exceptions;

public class UtentePresenteException extends Throwable {
    String message = "Utente già presente";
    @Override
    public String getMessage(){
        return message;
    }
}
