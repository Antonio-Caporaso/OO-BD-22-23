package Model.Entities;

import Model.Utilities.ActivityModel;

public class Intervallo extends ActivityModel {
    private String tipologia;

    public Intervallo() {
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getType() {
        return tipologia;
    }
}
