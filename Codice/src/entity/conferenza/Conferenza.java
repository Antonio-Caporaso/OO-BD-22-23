package entity.conferenza;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import entity.organizzazione.Ente;

public class Conferenza {

	private String nomeConferenza;
	private Date dataInizio;
	private Date dataFine;
	private String descrizione;
	private Set<Sponsor> sponsors;
	private Sede sede;
	private Programma programma;
	private Set<Ente> enti;
	//private Visibilita visibilita; //Potremmo fare una classe esterna per gestire la visibilità della conferenza




	// Default constructor
	public Conferenza() {
		sponsors = new HashSet<>();
		enti= new HashSet<>();
	}

	// Main constructor
	public Conferenza(String nomeConferenza, Date dataInizio, Date dataFine, String descrizione, Set<Sponsor> sponsors, Sede sede, Programma programma, Set<Ente> enti /*, Visibilita visibilita*/) {
		this.nomeConferenza = nomeConferenza;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.descrizione = descrizione;
		this.sponsors = sponsors;
		this.sede = sede;
		this.programma = programma;
		this.enti = enti ;
		//this.visibilita = visibilita;
	}

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

	public Set<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(Set<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public void addSponsor(Sponsor sponsor) {
		sponsors.add(sponsor);
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Programma getProgramma() {
		return programma;
	}

	public void setProgramma(Programma programma) {
		this.programma = programma;
	}

	public Set<Ente> getEnti() {
		return enti;
	}

	public void setEnti(Set<Ente> enti) {
		this.enti = enti;
	}
}
