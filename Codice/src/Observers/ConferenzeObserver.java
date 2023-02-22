package Observers;

import Model.Conferenze.Conferenza;

public interface ConferenzeObserver {
    void onConferenzaAdded(Conferenza conferenza);
    void onConferenzaRemoved(Conferenza conferenza);
}
