package Exceptions;

public class ExistingMemberException extends Exception {
    public String toString(){
        return "Il membro è già presente nel Comitato";
    }
}