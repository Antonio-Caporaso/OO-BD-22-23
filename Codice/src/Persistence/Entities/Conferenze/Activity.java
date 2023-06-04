package Persistence.Entities.Conferenze;

import java.sql.Timestamp;

public abstract class Activity {
    private Timestamp orario;
    public Activity() {

    }
    public Activity(Timestamp orario) {
        this.orario = orario;
    }

    public Timestamp getOrario() {
        return orario;
    }

    public void setOrario(Timestamp orario) {
        this.orario = orario;
    }

}


