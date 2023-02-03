package Model.organizzazione;

import Model.conferenza.Conferenza;

import java.util.LinkedList;
import java.util.Objects;

public class Ente {
    private String nome;
    private LinkedList<Conferenza> organizza;

    public Ente() {}

    public Ente(String nome, LinkedList<Conferenza> organizza) {
        this.nome = nome;
        this.organizza=organizza;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void addConferenza(Conferenza conferenza){
        if(!organizza.contains(conferenza)){
            organizza.add(conferenza);
        }
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
}