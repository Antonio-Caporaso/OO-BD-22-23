package Exceptions;

public class SediNonDisponibiliException extends Exception {
    String message = "Sedi non disponibili per il periodo indicato";
    @Override
    public String getMessage(){
        return this.message;
    }
}
