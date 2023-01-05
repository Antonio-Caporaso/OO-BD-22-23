package conferenza;
import java.util.Date;
public class Conferenza {

	private String nomeConferenza;
	private Date dataInizio;
	private Date dataFine;
	private String descrizione;
	//Va inserita la visibilità della conferenza (Questione dell'Enum)
	

	public String getNomeConferenza() {
		return nomeConferenza;
	}

	public void setNomeConferenza(String nomeConferenza) {
		this.nomeConferenza = nomeConferenza;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
