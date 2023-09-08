package Exceptions;

public class DateMismatchException extends Exception{
    private String message = "Le date inserite non combaciano con quelle della conferenza";

    @Override
    public String getMessage() {
        return message;
    }
}
