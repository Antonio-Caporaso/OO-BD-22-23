package Model.Entities.Conferenze;


import java.sql.Timestamp;

public class EventoSociale extends ActivityModel {
    private String tipologia;

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
    public String getType(){
        return tipologia;
    }
}
