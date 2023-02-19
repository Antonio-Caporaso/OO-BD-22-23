package Model.Conferenze.Conferenza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConferenzaModel {
    private List<Conferenza> conferenze = new ArrayList<>();
    private List<ConferenzeObserver> observers = new ArrayList<>();
    public void addConferenza(Conferenza conferenza){
        conferenze.add(conferenza);
        notifyObserversOnConferenzaAdded(conferenza);
    }

    private void notifyObserversOnConferenzaAdded(Conferenza conferenza) {
        for(ConferenzeObserver observer : observers){
            observer.onConferenzaAdded(conferenza);
        }
    }

    public void removeConferenza(Conferenza conferenza){
        conferenze.remove(conferenza);
        notifyObserversOnConferenzaRemoved(conferenza);
    }

    private void notifyObserversOnConferenzaRemoved(Conferenza conferenza) {
        for(ConferenzeObserver observer : observers){
            observer.onConferenzaRemoved(conferenza);
        }
    }

    public List<Conferenza> getConferenze(){
        return Collections.unmodifiableList(conferenze);
    }
    public void addObserver(ConferenzeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(ConferenzeObserver observer){
        observers.remove(observer);
    }
}
