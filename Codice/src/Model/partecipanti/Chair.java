package Model.partecipanti;

import Model.conferenza.Sessione;

public class Chair extends Partecipante{
    private int idChair;
    private Sessione gestisce;

    public int getIdChair() {
        return idChair;
    }

    public void setIdChair(int idChair) {
        this.idChair = idChair;
    }

    public Sessione getGestisce() {
        return gestisce;
    }

    public void setGestisce(Sessione gestisce) {
        this.gestisce = gestisce;
    }
}
