package Exceptions;
public class BlankFieldException extends Exception {
    String message = "Compilare tutti i campi";
    @Override
    public String getMessage(){
        return message;
    }
}
