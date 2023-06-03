package Persistence.Entities.organizzazione;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;


import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ActivityModel {
    private String attivita;
    private Timestamp dataInizio;
    private Timestamp dataFine;

    public ActivityModel() {

    }

    public ActivityModel(String attivita, Timestamp dataInizio, Timestamp dataFine) {
        this.attivita = attivita;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public String getAttivita() {
        return attivita;
    }

    public void setAttivita(String attivita) {
        this.attivita = attivita;
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
}
