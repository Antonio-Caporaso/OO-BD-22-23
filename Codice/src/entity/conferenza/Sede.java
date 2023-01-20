package entity.conferenza;

import java.util.Set;

public class Sede {
	
	private String nomeSede;
	private String indirizzo;
	private int capacita;
	private Boolean disponibilita; //utilizziamo gli accenti per definire gli attributi?
	private double costoAffitto;
	private Set<Sala> sale;

	//Main constructor
	public Sede(String nomeSede, String indirizzo, int capacita, Boolean disponibilita, double costoAffitto, Set<Sala> sale) {
		this.nomeSede = nomeSede;
		this.indirizzo = indirizzo;
		this.capacita = this.capacita;
		this.disponibilita = disponibilita;
		this.costoAffitto = costoAffitto;
		this.sale = sale;
	}

	//Methods getter and setter
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
	public int getCapacita() {
		return capacita;
	}
	public void setCapacita(int capacita) {
		this.capacita = capacita;
	}
	public Boolean getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(Boolean disponibilita) {
		this.disponibilita = disponibilita;
	}
	public double getCostoAffitto() {
		return costoAffitto;
	}
	public void setCostoAffitto(double costoAffitto) {
		this.costoAffitto = costoAffitto;
	}

	public Set<Sala> getSale() {
		return sale;
	}

	public void addSala(Sala sala) {
		sale.add(sala);
	}
}
