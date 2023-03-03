package Persistence.Entities.Conferenze;

import java.sql.Date;
import java.sql.Time;

public class EventoSociale {
    private int id;
    private Date data;
    private Time orario;
    private float costo;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getOrario() {
        return orario;
    }

    public void setOrario(Time orario) {
        this.orario = orario;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

}