package Persistence.Entities;

import java.util.Objects;

public class Utente {
    private int idUtente;
    private String titolo;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private String istituzione;

    public Utente(int idUtente, String titolo, String username, String password, String nome, String cognome, String email, String istituzione) {
        this.idUtente = idUtente;
        this.titolo = titolo;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.istituzione = istituzione;
    }
    public Utente(String nomeUtente, String cognomeUtente, String titoloUtente, String usernameUtente, String passwordUtente, String emailUtente, String istituzioneUtente) {
        this.nome = nomeUtente;
        this.cognome = cognomeUtente;
        this.titolo = titoloUtente;
        this.username = usernameUtente;
        this.password = passwordUtente;
        this.email = emailUtente;
        this.istituzione = istituzioneUtente;
    }
    public Utente(Utente utente){
        this.idUtente = utente.getIdUtente();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.istituzione = utente.getIstituzione();
        this.titolo = utente.getTitolo();
        this.username = utente.getUsername();
    }
    public Utente() {
    }

    public String getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(String istituzione) {
        this.istituzione = istituzione;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utente utente = (Utente) o;

        if (!Objects.equals(username, utente.username)) return false;
        return Objects.equals(email, utente.email);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return cognome+" "+nome;
    }
}
