package Persistence.Entities.organizzazione;

import java.util.Objects;

public class Comitato {
    private int id_comitato;
    private String tipologia;

    public Comitato() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comitato comitato = (Comitato) o;

        if (id_comitato != comitato.id_comitato) return false;
        return Objects.equals(tipologia, comitato.tipologia);
    }

    public int getId_comitato() {
        return id_comitato;
    }

    public void setId_comitato(int id_comitato) {
        this.id_comitato = id_comitato;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public int hashCode() {
        int result = id_comitato;
        result = 31 * result + (tipologia != null ? tipologia.hashCode() : 0);
        return result;
    }
}
