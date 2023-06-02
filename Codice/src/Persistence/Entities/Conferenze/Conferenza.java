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
    private float budget;
    private String valuta;
    private LinkedList<Ente> organizzataDa;
    private LinkedList<Sessione> sessioni;
    private LinkedList<Sponsorizzazione> sponsorizzataDa;

    public Conferenza(int conferenzaID, String nome, Utente proprietario, Timestamp dataInizio, Timestamp dataFine, String descrizione, Comitato comitatoLocale, Comitato comitatoScientifico, Sede sede, float budget, String valuta) {
        this.conferenzaID = conferenzaID;
        this.nome = nome;
        this.proprietario = proprietario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.descrizione = descrizione;
        this.comitatoLocale = comitatoLocale;
        this.comitatoScientifico = comitatoScientifico;
        this.sede = sede;
        this.budget = budget;
        this.valuta = valuta;
    }

    public Conferenza() {
    }

    public Conferenza(String nome, Timestamp dataI, Timestamp dataF, String descrizione, float budget, Sede sede, Utente user, String valuta) {
        this.nome = nome;
        this.dataInizio=dataI;
        this.dataFine=dataF;
        this.descrizione=descrizione;
        this.budget=budget;
        this.sede=sede;
        this.proprietario=user;
        this.valuta=valuta;
    }

    public Conferenza(int id, String nome, Utente proprietario, Timestamp datainizio, Timestamp datafine, String descrizione, Comitato local, Comitato scientific, Sede sede, float budget) {
        this.conferenzaID=id;
        this.nome=nome;
        this.proprietario=proprietario;
        this.dataInizio=datainizio;
        this.dataFine=datafine;
        this.descrizione=descrizione;
        this.comitatoLocale=local;
        this.comitatoScientifico=scientific;
        this.sede=sede;
        this.budget=budget;
    }

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
    public float getBudget() {
        return budget;
    }
    public void setBudget(float budget) {
        this.budget = budget;
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
    public void setValuta(String valuta){
        if(valuta.equals("USD"))
            this.valuta= "$";
        else if(valuta.equals("EUR"))
            this.valuta="€";
        else if(valuta.equals("GBP"))
            this.valuta="£";
        else if(valuta.equals("JPY"))
            this.valuta="¥";
    }
    public String getCodiceValuta()
    {
        if(valuta.equals("$"))
            return "USD";
        else if(valuta.equals("€"))
            return "EUR";
        else if(valuta.equals("£"))
            return "GBP";
        else if(valuta.equals("¥"))
            return "JPY";
        else
            return null;
    }

    public String getValuta() {
        return valuta;
    }

    @Override
    public String toString() {
        return nome;
    }
}