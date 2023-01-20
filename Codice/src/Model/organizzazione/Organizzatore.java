package Model.organizzazione;

import Model.exceptions.ExistingMemberException;
import Model.organizzazione.Comitato;

class Organizzatore  {
    private Comitato appartieneA;

    public void setSceltoDa(Comitato appartieneA) throws ExistingMemberException {
        this.appartieneA = appartieneA;
        appartieneA.add(this);
    }
    public Comitato getAppartieneA(){
        return appartieneA;
    }

}