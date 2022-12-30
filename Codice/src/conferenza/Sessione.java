package conferenza;

import java.sql.Date;

public class Sessione {
	
	private String nomeSessione;
	private Date oraInizio;
	private Date oraFine;
	private String descrizione;
	
	
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
	
	

}
