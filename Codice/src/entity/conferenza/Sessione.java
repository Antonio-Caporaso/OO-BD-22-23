package entity.conferenza;

import java.sql.Date;
import entity.utenti.*;

public class Sessione {
	
	private String nomeSessione;
	private Date oraInizio;
	private Date oraFine;
	private String descrizione;
	private ProgrammaSessione programmaSessione;
	private Chair chair; //Ho messo Chair in sessione, poiché è il responsabile della sessione, quindi non ha senso averlo in programma
	private Spettatore spettatore; //spettatore partecipa alla sessione, quindi necessita di essere qui
	//keynote speaker?
	
	public String getNomeSessione() {
		return nomeSessione;
	}
	public void setNomeSessione(String nomeSessione) {
		this.nomeSessione = nomeSessione;
	}
	public Date getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(Date oraInizio) {
		this.oraInizio = oraInizio;
	}
	public Date getOraFine() {
		return oraFine;
	}
	public void setOraFine(Date oraFine) {
		this.oraFine = oraFine;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public ProgrammaSessione getProgrammaSessione() {
		return programmaSessione;
	}

	public void setProgrammaSessione(ProgrammaSessione programmaSessione) {
		this.programmaSessione = programmaSessione;
	}

	public Chair getChair() {
		return chair;
	}

	public void setChair(Chair chair) {
		this.chair = chair;
	}

	public Spettatore getSpettatore() {
		return spettatore;
	}

	public void setSpettatore(Spettatore spettatore) {
		this.spettatore = spettatore;
	}
}
