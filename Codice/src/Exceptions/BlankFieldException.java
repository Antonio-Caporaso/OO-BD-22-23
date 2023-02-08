package Exceptions;

import java.security.spec.ECFieldF2m;

public class BlankFieldException extends Exception {
    String message = "Compilare tutti i campi";
    @Override
    public String getMessage(){
        return message;
    }
}
