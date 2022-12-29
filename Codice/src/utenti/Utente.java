package utenti;

import Organizzazione.Ente;

import java.util.Date;

public class Utente {
    private String titolo;
    private String nome;
    private String cognome;
    private String email;
    private Ente istituzione;
    private String password;
    private Date dataRegistrazione;

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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }
}
