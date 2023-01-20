package Model.organizzazione;
import Exceptions.ExistingMemberException;

import java.util.HashSet;

abstract class Comitato {
    private Organizzatore responsabile;
    private HashSet<Organizzatore> membri;

    void setResponsabile(Organizzatore responsabile){
        this.responsabile = responsabile;
        membri.add(responsabile);
    }
    public void add(Organizzatore organizzatore) throws ExistingMemberException {
        if(membri.contains(organizzatore))
            throw new ExistingMemberException();
        else
            membri.add(organizzatore);
    }
}
