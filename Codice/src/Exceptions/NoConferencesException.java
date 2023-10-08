package Exceptions;

public class NoConferencesException extends Exception {
    private String message = "Non risultano conferenze";
    @Override
    public String toString(){
        return message;
    }
}
