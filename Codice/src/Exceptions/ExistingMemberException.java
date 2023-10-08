package Exceptions;

public class ExistingMemberException extends Exception {
    private String message = "Il membro già appartiene al comitato";
    public String toString(){
        return message;
    }
}