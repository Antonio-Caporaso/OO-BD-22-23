package Persistence.Entities.organizzazione;

import Persistence.Entities.Conferenze.Conferenza;

import java.util.LinkedList;
import java.util.Objects;

public class Ente {
    private int enteID;
    private String nome;
    private String sigla;

    public Ente() {}

    public Ente(String nome) {
        this.nome = nome;
    }

    public Ente(int id, String nome, String sigla) {
        this.enteID = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    public int getEnteID() {
        return enteID;
    }

    public void setEnteID(int enteID) {
        this.enteID = enteID;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ente ente = (Ente) o;

        return Objects.equals(nome, ente.nome);
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return nome;
    }
}