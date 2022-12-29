package Organizzazione;

public class Ente {
	private String nomeEnte;
	private String tipologia;
	private String indirizzo;
	private String telefono;
	private String email;
	
	public Ente() {	}
	
	public Ente(String nomeEnte){
		this.setNomeEnte(nomeEnte);
	}
	
	public Ente(String nomeEnte, String tipologia) {
		this.setNomeEnte(nomeEnte);
		this.setTipologia(tipologia);
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
		String details = "Ente: "+this.getNomeEnte()+", tipologia: " + this.getTipologia() +", indirizzo: "
				+ this.getIndirizzo() +", telefono: " + this.getTelefono() +", email: "
				+ this.getEmail();
		return details;
	}

	void nomina(Comitato comitato){

	}
}
