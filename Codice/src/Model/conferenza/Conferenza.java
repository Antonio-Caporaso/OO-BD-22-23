package Model.conferenza;

import Model.Utente;
import Model.organizzazione.ComitatoLocale;
import Model.organizzazione.ComitatoScientifico;
import Model.organizzazione.Ente;
import Model.organizzazione.Sponsor;

import java.util.Date;
import java.util.LinkedList;

public class Conferenza {
    private Utente proprietario;
    private Date dataInizio;
    private Date dataFine;
    private String descrizione;
    private LinkedList<Ente> organizzataDa;
    private LinkedList<Sponsorizzazione> sponsorizzataDa;
    private ComitatoLocale comitatoLocale;
    private ComitatoScientifico comitatoScientifico;
    private Sede sede;
    private float budget;
    private LinkedList<Sessione> sessioni;

    public Conferenza(Date dataInizio, Date dataFine, String descrizione, LinkedList<Ente> organizzataDa, LinkedList<Sponsorizzazione> sponsorizzataDa, ComitatoLocale comitatoLocale, ComitatoScientifico comitatoScientifico, Sede sede, LinkedList<Sessione> sessioni) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.descrizione = descrizione;
        this.organizzataDa = organizzataDa;
        this.sponsorizzataDa = sponsorizzataDa;
        this.comitatoLocale = comitatoLocale;
        this.comitatoScientifico = comitatoScientifico;
        this.sede = sede;
        this.sessioni = sessioni;
    }

    public Utente getProprietario() {
        return proprietario;
    }
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
    public Date getDataInizio() {
        return dataInizio;
    }
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }
    public Date getDataFine() {
        return dataFine;
    }
    public void setDataFine(Date dataFine) {
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
    public ComitatoLocale getComitatoLocale() {
        return comitatoLocale;
    }
    public void setComitatoLocale(ComitatoLocale comitatoLocale) {
        this.comitatoLocale = comitatoLocale;
    }
    public ComitatoScientifico getComitatoScientifico() {
        return comitatoScientifico;
    }
    public void setComitatoScientifico(ComitatoScientifico comitatoScientifico) {
        this.comitatoScientifico = comitatoScientifico;
    }
    public void addEnte(Ente ente){
        if(!organizzataDa.contains(ente)){
            organizzataDa.add(ente);
            ente.addConferenza(this);
        }
    }
    public void addSponsorizzazione(Sponsorizzazione sponsorizzazione){
        if(!sponsorizzataDa.contains(sponsorizzazione)) {
            sponsorizzataDa.add(sponsorizzazione);
            sponsorizzazione.setConferenza(this);
        }
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
    public void addSessione(Sessione sessione){
        if(!sessioni.contains(sessione)){
            sessioni.add(sessione);
            sessione.setConferenza(this);
        }
    }
}