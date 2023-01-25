package Model.partecipanti;

import Model.conferenza.Intervento;

public class Speaker extends Partecipante{
    private Intervento effettua;

    public Intervento getEffettua() {
        return effettua;
    }

    public void setEffettua(Intervento effettua) {
        this.effettua = effettua;
        effettua.setSpeaker(this);
    }
}
