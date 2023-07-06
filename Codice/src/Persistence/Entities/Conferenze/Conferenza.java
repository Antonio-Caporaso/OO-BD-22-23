package Persistence.Entities.Conferenze;

import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Comitato;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;

import java.sql.Timestamp;
import java.util.LinkedList;

public class Conferenza {
    private int id_conferenza;
    private String titolo;
    private Utente proprietario;
    private Timestamp inizio;
    private Timestamp fine;
    private String descrizione;
    private Comitato comitato_l;
    private Comitato comitato_s;
    private Sede sede;
    private LinkedList<Ente> organizzataDa;

    public Conferenza(int conferenzaID, String titolo, Utente proprietario, Timestamp inizio, Timestamp fine, String descrizione, Comitato comitato_l, Comitato comitato_s, Sede sede) {
        this.id_conferenza = conferenzaID;
        this.titolo = titolo;
        this.proprietario = proprietario;
        this.inizio = inizio;
        this.fine = fine;
        this.descrizione = descrizione;
        this.comitato_l = comitato_l;
        this.comitato_s = comitato_s;
        this.sede = sede;
    }

    public Conferenza() {}

    public int getId_conferenza() {
        return id_conferenza;
    }

    public void setId_conferenza(int id_conferenza) {
        this.id_conferenza = id_conferenza;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Utente getProprietario() {
        return proprietario;
    }
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
    public Timestamp getInizio() {
        return inizio;
    }
    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }
    public Timestamp getFine() {
        return fine;
    }
    public void setFine(Timestamp fine) {
        this.fine = fine;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public Comitato getComitato_l() {
        return comitato_l;
    }
    public void setComitato_l(Comitato comitato_l) {
        this.comitato_l = comitato_l;
    }
    public Comitato getComitato_s() {
        return comitato_s;
    }
    public void setComitato_s(Comitato comitato_s) {
        this.comitato_s = comitato_s;
    }
    public Sede getSede() {
        return sede;
    }
    public void setSede(Sede sede) {
        this.sede = sede;
    }
    @Override
    public String toString() {
        return titolo;
    }
}