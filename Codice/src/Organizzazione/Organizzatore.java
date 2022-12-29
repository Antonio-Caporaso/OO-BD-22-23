package Organizzazione;

public class Organizzatore {
	private String titolo;
	private String nome;
	private String cognome;
	private String email;
	private Ente istituzione;
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Ente getIstituzione() {
		return istituzione;
	}
	public void setIstituzione(Ente istituzione) {
		this.istituzione = istituzione;
	}
}
