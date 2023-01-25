package Model.partecipanti;

import Model.conferenza.Sessione;

public class Chair extends Partecipante{
    private Sessione gestisce;

    public Sessione getGestisce() {
        return gestisce;
    }

    public void setGestisce(Sessione gestisce) {
        this.gestisce = gestisce;
    }
}
