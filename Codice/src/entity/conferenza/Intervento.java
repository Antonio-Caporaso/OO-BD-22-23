package entity.conferenza;

import java.sql.Date;

public class Intervento {
	
	private String titolo;
	private String abstractIntervento; //Cambiato nome in abstractInverneto poiché abstract è una parola chiave di Java
	private Date orarioInizio;
	private Date orarioFine;
	
	
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
	
	
	

}
