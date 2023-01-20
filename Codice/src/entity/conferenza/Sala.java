package entity.conferenza;

public class Sala {
	
	private String nomeSala;
	private String capacità;
	private Boolean prenotata;
	private Sede sede;
	
	
	public String getNomeSala() {
		return nomeSala;
	}
	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
	}
	public String getCapacità() {
		return capacità;
	}
	public void setCapacità(String capacità) {
		this.capacità = capacità;
	}
	public Boolean isPrenotata() {
		return prenotata;
	}
	public void setPrenotata(Boolean prenotata) {
		this.prenotata = prenotata;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}
}
