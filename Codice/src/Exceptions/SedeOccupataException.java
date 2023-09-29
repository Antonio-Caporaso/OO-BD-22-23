package Exceptions;

public class SedeOccupataException extends Exception {
    String message = "Sede già occupata per il periodo indicato";
    @Override
    public String getMessage(){
        return this.message;
    }
}