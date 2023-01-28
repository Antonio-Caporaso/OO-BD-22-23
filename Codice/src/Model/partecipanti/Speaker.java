package Model.partecipanti;

import Model.conferenza.Intervento;

public class Speaker extends Partecipante{
    private int idSpeaker;
    private Intervento effettua;
    private boolean isKeynote;

    public Intervento getEffettua() {
        return effettua;
    }
    public void setEffettua(Intervento effettua) {
        this.effettua = effettua;
        effettua.setSpeaker(this);
    }

    public boolean isKeynote() {
        return isKeynote;
    }

    public void setKeynote(boolean flag) {
        isKeynote = flag;
    }
}
