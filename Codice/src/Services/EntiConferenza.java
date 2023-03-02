package Services;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntiConferenza {
    private ObservableList<Ente> enti;
    private Conferenza conferenza;
    public EntiConferenza(Conferenza conferenza){
        enti = FXCollections.observableArrayList();
        this.conferenza = conferenza;
        enti.addAll(conferenza.getOrganizzataDa());
    }
    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }

    public void addEnte(Ente e) {
        enti.add(e);
    }
    public void removeEnte(Ente e){
        enti.remove(e);
    }
}
