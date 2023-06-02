package Persistence.Entities.organizzazione;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ActivityModel {
    private String attivita;
    private Timestamp dataInizio;
    private Timestamp dataFine;

    public ActivityModel(String attivita, Timestamp dataInizio, Timestamp dataFine) {
        this.attivita = attivita;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public String getAttivita() {
        return attivita;
    }

    public String attivitaProperty() {
        return attivita;
    }

    public Timestamp getDataInizio() {
        return dataInizio;
    }

    public Timestamp dataInizioProperty() {
        return dataInizio;
    }

    public Timestamp getDataFine() {
        return dataFine;
    }

    public Timestamp dataFineProperty() {
        return dataFine;
    }
}
