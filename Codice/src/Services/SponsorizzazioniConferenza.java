package Services;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SponsorizzazioniConferenza {
    private ObservableList<Sponsorizzazione> sponsorizzazioni;
    private Conferenza conferenza;
    public SponsorizzazioniConferenza(Conferenza conferenza){
        sponsorizzazioni = FXCollections.observableArrayList();
        this.conferenza = conferenza;
        sponsorizzazioni.addAll(conferenza.getSponsorizzataDa());
    }

    public ObservableList<Sponsorizzazione> getSponsorizzazioni() {
        return sponsorizzazioni;
    }

    public void setSponsorizzazioni(ObservableList<Sponsorizzazione> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }
    public void addSponsorizzazione(Sponsorizzazione s){
        //Metodo dao per il salvataggio
        sponsorizzazioni.add(s);
    }
    public void removeSponsorizzazione(Sponsorizzazione s){
        // Metodo dao per la rimozione
        sponsorizzazioni.remove(s);
    }
}
