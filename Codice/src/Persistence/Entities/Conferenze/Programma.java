package Persistence.Entities.Conferenze;
import Persistence.Entities.partecipanti.Speaker;
public class Programma {
    private int programmaID;
    private Sessione sessione;
    private Speaker keynote;
    public Programma(Sessione sessione) {
        this.sessione = sessione;
    }
    public Programma() {}
    public Speaker getKeynote() {
        return keynote;
    }
    public void setKeynote(Speaker keynote) {
        this.keynote = keynote;
    }
    public Sessione getSessione() {
        return sessione;
    }
    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }
    public int getProgrammaID() {
        return programmaID;
    }
    public void setProgrammaID(int programmaID) {
        this.programmaID = programmaID;
    }
}
