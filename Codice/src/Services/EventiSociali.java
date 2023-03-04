package Services;

import Persistence.DAO.EventoSocialeDao;
import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventiSociali {
    private ObservableList<EventoSociale> eventi;
    private Programma programma;
    public EventiSociali(Programma programma){
        eventi = FXCollections.observableArrayList();
        this.programma = programma;
    }
    public void loadEventiSociali(){
        EventoSocialeDao dao = new EventoSocialeDao();
        eventi.clear();
        eventi.addAll(dao.retrieveEventiByProgramma(programma));
    }
}
