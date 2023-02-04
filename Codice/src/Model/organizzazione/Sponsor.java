package Model.organizzazione;

import java.util.LinkedList;

public class Sponsor {
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
}
