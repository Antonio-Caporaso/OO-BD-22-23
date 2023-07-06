package Persistence.Entities.organizzazione;

import java.util.LinkedList;

public class Sponsor {
    private int id_sponsor;
    private String nome;
    private LinkedList<Sponsorizzazione> sponsorizzazioni;

    public Sponsor() {}
    public Sponsor(String nome, LinkedList<Sponsorizzazione> sponsorizzazioni) {
        this.nome = nome;
        this.sponsorizzazioni = sponsorizzazioni;
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

    public LinkedList<Sponsorizzazione> getSponsorizzazioni() {
        return sponsorizzazioni;
    }

    public void setSponsorizzazioni(LinkedList<Sponsorizzazione> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void addSponsorizzazione(Sponsorizzazione sponsorizzazione){
        if(!sponsorizzazioni.contains(sponsorizzazione)){
            sponsorizzazioni.add(sponsorizzazione);
            sponsorizzazione.setSponsor(this);
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
