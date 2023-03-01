package Persistence.Entities.organizzazione;
import Exceptions.ExistingMemberException;

import java.util.HashSet;
import java.util.Objects;

abstract class Comitato {
    private int comitatoID;
    private Organizzatore responsabile;
    private HashSet<Organizzatore> membri;

    public Comitato() {}

    public Comitato(Organizzatore responsabile, HashSet<Organizzatore> membri) {
        this.responsabile = responsabile;
        this.membri = membri;
    }

    void setResponsabile(Organizzatore responsabile){
        this.responsabile = responsabile;
        membri.add(responsabile);
    }

    public Organizzatore getResponsabile() {
        return responsabile;
    }

    public void add(Organizzatore organizzatore) throws ExistingMemberException {
        if(membri.contains(organizzatore))
            throw new ExistingMemberException();
        else
            membri.add(organizzatore);
    }

    public int getComitatoID() {
        return comitatoID;
    }

    public void setComitatoID(int comitatoID) {
        this.comitatoID = comitatoID;
    }

    public HashSet<Organizzatore> getMembri() {
        return membri;
    }

    public void setMembri(HashSet<Organizzatore> membri) {
        this.membri = membri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comitato comitato = (Comitato) o;

        if (!Objects.equals(responsabile, comitato.responsabile))
            return false;
        return Objects.equals(membri, comitato.membri);
    }

    @Override
    public int hashCode() {
        int result = responsabile != null ? responsabile.hashCode() : 0;
        result = 31 * result + (membri != null ? membri.hashCode() : 0);
        return result;
    }
}
