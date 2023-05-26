package Persistence.Entities.organizzazione;

import Exceptions.ExistingMemberException;

import java.util.HashSet;
import java.util.Objects;

public class Comitato {
    private int comitatoID;
    private String tipologia;
    private HashSet<Organizzatore> membri;

    public void setTipologia(String tipologia) {
        this.tipologia=tipologia;
    }

    public Comitato() {}

    public Comitato(HashSet<Organizzatore> membri) {
        this.membri = membri;
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

        if (comitatoID != comitato.comitatoID) return false;
        return Objects.equals(tipologia, comitato.tipologia);
    }

    @Override
    public int hashCode() {
        int result = comitatoID;
        result = 31 * result + (tipologia != null ? tipologia.hashCode() : 0);
        return result;
    }
}
