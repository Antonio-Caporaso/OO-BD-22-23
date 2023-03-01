package Exceptions;

public class SessioneNotSelectedException extends Exception {
    private String message = "Nessuna sessione selezionata";

    public String getMessage() {
        return message;
    }
}
