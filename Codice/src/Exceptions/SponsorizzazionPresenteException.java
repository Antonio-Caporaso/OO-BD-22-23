package Exceptions;

public class SponsorizzazionPresenteException extends Throwable {
    private String message = "Sponsorizzazione già presente";
    public String getMessage(){
        return message;
    }
}
