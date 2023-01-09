package entity.conferenza;

import java.sql.Date;
import entity.utenti.Speaker;

public class Intervento {
	
	private String titolo;
	private String abstractIntervento; //Cambiato nome in abstractInverneto poiché abstract è una parola chiave di Java
	private Date orarioInizio;
	private Date orarioFine;
	private Speaker speaker;
	//keynote speaker?
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getAbstractIntervento() {
		return abstractIntervento;
	}
	public void setAbstractIntervento(String abstractIntervento) {
		this.abstractIntervento = abstractIntervento;
	}
	public Date getOrarioInizio() {
		return orarioInizio;
	}
	public void setOrarioInizio(Date orarioInizio) {
		this.orarioInizio = orarioInizio;
	}
	public Date getOrarioFine() {
		return orarioFine;
	}
	public void setOrarioFine(Date orarioFine) {
		this.orarioFine = orarioFine;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}
}
