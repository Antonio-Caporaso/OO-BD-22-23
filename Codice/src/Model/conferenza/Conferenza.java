package Model.conferenza;

import Model.organizzazione.ComitatoLocale;
import Model.organizzazione.ComitatoScientifico;
import Model.organizzazione.Ente;
import Model.users.Sponsor;
import java.util.Date;

public class Conferenza {
    private Date dataInizio;
    private Date dataFine;
    private String descrizione;
    private Ente[] organizzataDa;
    private Sponsor[] sponsorizzataDa;
    private ComitatoLocale comitatoLocale;
    private ComitatoScientifico comitatoScientifico;
    private Sede sede;
    private Sessione[] sessioni;

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
    public Ente[] getOrganizzataDa() {
        return organizzataDa;
    }
    public void setOrganizzataDa(Ente[] organizzataDa) {
        this.organizzataDa = organizzataDa;
    }
    public Sponsor[] getSponsorizzataDa() {
        return sponsorizzataDa;
    }
    public void setSponsorizzataDa(Sponsor[] sponsorizzataDa) {
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
    public Sede getSede() {
        return sede;
    }
    public void setSede(Sede sede) {
        this.sede = sede;
        sede.addConferenza(this);
    }
    public Sessione[] getSessioni() {
        return sessioni;
    }
    public void setSessioni(Sessione[] sessioni) {
        this.sessioni = sessioni;
    }
}