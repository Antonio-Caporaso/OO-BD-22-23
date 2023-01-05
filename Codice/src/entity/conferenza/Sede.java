package entity.conferenza;

public class Sede {
	
	private String nomeSede;
	private String indirizzo;
	private int capacità;
	private Boolean disponibilità; //utilizziamo gli accenti per definire gli attributi?
	private double costoAffitto;
	
	public String getNomeSede() {
		return nomeSede;
	}
	public void setNomeSede(String nomeSede) {
		this.nomeSede = nomeSede;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public int getCapacità() {
		return capacità;
	}
	public void setCapacità(int capacità) {
		this.capacità = capacità;
	}
	public Boolean getDisponibilità() {
		return disponibilità;
	}
	public void setDisponibilità(Boolean disponibilità) {
		this.disponibilità = disponibilità;
	}
	public double getCostoAffitto() {
		return costoAffitto;
	}
	public void setCostoAffitto(double costoAffitto) {
		this.costoAffitto = costoAffitto;
	}
	
	
	
	

}
