package entity.organizzazione;

import entity.utenti.Utente;

class Organizzatore extends entity.utenti.Utente {
    private Comitato appartieneA;

    public void setSceltoDa(Comitato appartieneA){
        this.appartieneA = appartieneA;
        appartieneA.addMembro(this);
    }
    public Comitato getAppartieneA(){
        return appartieneA;
    }

    @Override
    public String toString() {
        return super.toString()+", ruolo: Organizzatore." +
                "Lavora nel comitato=" + appartieneA +
                '}';
    }
}