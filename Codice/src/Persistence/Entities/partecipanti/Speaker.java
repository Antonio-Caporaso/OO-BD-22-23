package Persistence.Entities.partecipanti;

import Persistence.Entities.Conferenze.Intervento;

import java.util.LinkedList;

public class Speaker extends Partecipante{
    private int idSpeaker;
    public int getIdSpeaker() {
        return idSpeaker;
    }
    public void setIdSpeaker(int idSpeaker) {
        this.idSpeaker = idSpeaker;
    }
}
