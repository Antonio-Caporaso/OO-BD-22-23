package entity.organizzazione;

import entity.conferenza.Conferenza;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Ente {
	private LinkedList<Conferenza> conferenzeOrganizzate;
	private String nomeEnte;
	private String tipologia;
	private String indirizzo;
	private String telefono;
	private String email;
	private ArrayList<Comitato> comitatiNominati;
	
	public Ente() {	}
	
	public Ente(String nomeEnte){
		this.setNomeEnte(nomeEnte);
	}
	
	public Ente(String nomeEnte, String tipologia) {
		this.setNomeEnte(nomeEnte);
		this.setTipologia(tipologia);
		this.comitatiNominati = new ArrayList<Comitato>();
	}
	public void addConferenza(Conferenza conferenza){
		if(!conferenzeOrganizzate.contains(conferenza)) {
			conferenzeOrganizzate.add(conferenza);
			conferenza.addEnte(this);
		}
	}
	public String getNomeEnte() {
		return nomeEnte;
	}
	
	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Ente: "+this.getNomeEnte()+", tipologia: " + this.getTipologia() +", indirizzo: "
				+ this.getIndirizzo() +", telefono: " + this.getTelefono() +", email: "
				+ this.getEmail();
	}

	void addComitatiNominati(Comitato comitato){
		if(!comitatiNominati.contains(comitato))
			comitatiNominati.add(comitato);
	}

	void showComitatiNominati(){
		Iterator<Comitato> i = comitatiNominati.iterator();
		while(i.hasNext()) {
			Comitato com = (Comitato) i.next();
			System.out.println(com.toString());
		}
	}

}
