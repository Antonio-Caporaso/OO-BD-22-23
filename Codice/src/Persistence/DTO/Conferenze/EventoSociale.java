package Persistence.DTO.Conferenze;

import java.util.Date;

public class EventoSociale {

    private String tipologiaEvento;
    private double costo;
    private Date orario;


    public String getTipologiaEvento() {
        return tipologiaEvento;
    }
    public void setTipologiaEvento(String tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;
    }
    public double getCosto() {
        return costo;
    }
    public void setCosto(double costo) {
        this.costo = costo;
    }
    public Date getOrario() {
        return orario;
    }
    public void setOrario(Date orario) {
        this.orario = orario;
    }

}