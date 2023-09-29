package Exceptions;

public class DateMismatchException extends Exception{
    private String message = "Verifica il corretto inserimento delle date";

    @Override
    public String getMessage() {
        return message;
    }
}
