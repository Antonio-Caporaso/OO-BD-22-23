package Persistence.Entities.Conferenze;

import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.*;

import java.sql.Timestamp;
import java.util.LinkedList;

public class Conferenza {
    private int conferenzaID;
    private String nome;
    private Utente proprietario;
    private Timestamp dataInizio;
    private Timestamp dataFine;
    private String descrizione;
    private LinkedList<Ente> organizzataDa;
    private LinkedList<Sponsorizzazione> sponsorizzataDa;
    private Comitato comitatoLocale;
    private Comitato comitatoScientifico;
    private Sede sede;
    private float budget;
    private String valuta;
    private LinkedList<Sessione> sessioni;
    public Conferenza(int id, String nome, Timestamp dataInizio,
                      Timestamp dataFine, String descrizione,
                      Sede sede, float budget, ComitatoLocale comitato,
                      ComitatoScientifico c2, Utente proprietario, String valuta){
    this.conferenzaID = id;
    this.nome = nome;
    this.dataInizio = dataInizio;
    this.dataFine = dataFine;
    this.descrizione = descrizione;
    this.sede = sede;
    this.budget = budget;
    this.comitatoLocale = comitato;
    this.comitatoScientifico = c2;
    this.proprietario = proprietario;
    this.valuta = valuta;
    }
    public Conferenza(String nome, Timestamp dataInizio, Timestamp dataFine, String descrizione) {
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.descrizione = descrizione;
    }

    public Conferenza(String nome, Timestamp dataI, Timestamp dataF, String descrizione, float budget, Sede sede, Utente user, String valuta) {
        this.nome=nome;
        this.dataInizio = dataI;
        this.dataFine = dataF;
        this.descrizione= descrizione;
        this.budget= budget;
        this.sede = sede;
        this.proprietario = user;
        this.valuta = valuta;
    }

    public Conferenza(int id, String nome, Timestamp datainizio, Timestamp datafine, Comitato comitatoScientifico, Comitato comitatoLocale, String descrizione, Sede sede, float budget, Utente proprietario) {
        this.conferenzaID = id;
        this.nome = nome;
        this.dataInizio=datainizio;
        this.dataFine= datafine;
        this.descrizione = descrizione;
        this.sede = sede;
        this.budget=budget;
        this.proprietario=proprietario;
        this.comitatoLocale = comitatoLocale;
        this.comitatoScientifico = comitatoScientifico;
    }

    public Conferenza() {
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
    public LinkedList<Ente> getOrganizzataDa() {
        return organizzataDa;
    }
    public void setOrganizzataDa(LinkedList<Ente> organizzataDa) {
        this.organizzataDa = organizzataDa;
    }
    public LinkedList<Sponsorizzazione> getSponsorizzataDa() {
        return sponsorizzataDa;
    }
    public void setSponsorizzataDa(LinkedList<Sponsorizzazione> sponsorizzataDa) {
        this.sponsorizzataDa = sponsorizzataDa;
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
    public LinkedList<Sessione> getSessioni() {
        return sessioni;
    }
    public void setSessioni(LinkedList<Sessione> sessioni) {
        this.sessioni = sessioni;
    }
    public String getCodiceValuta(){
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
        this.valuta=valuta;
    }

    public String getValuta() {
        return valuta;
    }

    @Override
    public String toString() {
        return nome;
    }
}