package Model.Entities;

import java.util.LinkedList;

public class Sponsor {
    private int id_sponsor;
    private String nome;

    public Sponsor() {
    }

    public Sponsor(String nome) {
        this.nome = nome;
    }


    public Sponsor(int id, String nome) {
        this.nome = nome;
        this.id_sponsor = id;
    }

    public int getId_sponsor() {
        return id_sponsor;
    }

    public void setId_sponsor(int id_sponsor) {
        this.id_sponsor = id_sponsor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
