package Persistence.Entities.organizzazione;

import Persistence.Entities.Conferenze.Conferenza;

import java.util.LinkedList;
import java.util.Objects;

public class Ente {
    private int enteID;
    private String nome;

    public Ente() {}

    public Ente(String nome) {
        this.nome = nome;
    }

    public Ente(int id, String nome) {
        this.enteID = id;
        this.nome = nome;
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