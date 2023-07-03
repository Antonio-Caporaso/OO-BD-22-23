package Persistence.Entities.Conferenze;

import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Comitato;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;

import java.sql.Timestamp;
import java.util.LinkedList;

public class Conferenza {
    private int conferenzaID;
    private String nome;
    private Utente proprietario;
    private Timestamp dataInizio;
    private Timestamp dataFine;
    private String descrizione;
    private Comitato comitatoLocale;
    private Comitato comitatoScientifico;
    private Sede sede;
    private LinkedList<Ente> organizzataDa;
    private LinkedList<Sessione> sessioni;
    private LinkedList<Sponsorizzazione> sponsorizzataDa;


    public Conferenza(int conferenzaID, String nome, Utente proprietario, Timestamp dataInizio, Timestamp dataFine, String descrizione, Comitato comitatoLocale, Comitato comitatoScientifico, Sede sede) {
        this.conferenzaID = conferenzaID;
        this.nome = nome;
        this.proprietario = proprietario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.descrizione = descrizione;
        this.comitatoLocale = comitatoLocale;
        this.comitatoScientifico = comitatoScientifico;
        this.sede = sede;
    }

    public Conferenza() {}


    public LinkedList<Ente> getOrganizzataDa() {
        return organizzataDa;
    }

    public void setOrganizzataDa(LinkedList<Ente> organizzataDa) {
        this.organizzataDa = organizzataDa;
    }

    public LinkedList<Sessione> getSessioni() {
        return sessioni;
    }

    public void setSessioni(LinkedList<Sessione> sessioni) {
        this.sessioni = sessioni;
    }

    public LinkedList<Sponsorizzazione> getSponsorizzataDa() {
        return sponsorizzataDa;
    }

    public void setSponsorizzataDa(LinkedList<Sponsorizzazione> sponsorizzatoDa) {
        this.sponsorizzataDa = sponsorizzatoDa;
    }

    public int getConferenzaID() {
        return conferenzaID;
    }

    public void setConferenzaID(int conferenzaID) {
        this.conferenzaID = conferenzaID;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Utente getProprietario() {
        return proprietario;
    }
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
    public Timestamp getDataInizio() {
        return dataInizio;
    }
    public void setDataInizio(Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }
    public Timestamp getDataFine() {
        return dataFine;
    }
    public void setDataFine(Timestamp dataFine) {
        this.dataFine = dataFine;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public Comitato getComitatoLocale() {
        return comitatoLocale;
    }
    public void setComitatoLocale(Comitato comitatoLocale) {
        this.comitatoLocale = comitatoLocale;
    }
    public Comitato getComitatoScientifico() {
        return comitatoScientifico;
    }
    public void setComitatoScientifico(Comitato comitatoScientifico) {
        this.comitatoScientifico = comitatoScientifico;
    }
    public Sede getSede() {
        return sede;
    }
    public void setSede(Sede sede) {
        this.sede = sede;
    }
    public String setCodiceValuta(String valuta){
        if(valuta.equals("$"))
            return "USD";
        else if(valuta.equals("€"))
            return "EUR";
        else if(valuta.equals("£"))
            return  "GBP";
        else if(valuta.equals("¥"))
            return "JPY";
        else return null;
    }
    @Override
    public String toString() {
        return nome;
    }
}