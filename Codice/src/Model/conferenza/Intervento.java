package Model.conferenza;

public class Intervento {
    private Date orarioIntervento;
    private String estratto;
    private Speaker speaker;

    public void setSpeaker(Speaker speaker){
        this.speaker = speaker;
    }

    public Speaker getSpeaker(){
        return speaker;
    }

    public Date getOrarioIntervento() {
        return orarioIntervento;
    }

    public void setOrarioIntervento(Date orarioIntervento) {
        this.orarioIntervento = orarioIntervento;
    }

    public String getEstratto() {
        return estratto;
    }

    public void setEstratto(String estratto) {
        this.estratto = estratto;
    }
}