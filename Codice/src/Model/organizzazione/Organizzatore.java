package Model.organizzazione;

import Exceptions.ExistingMemberException;

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