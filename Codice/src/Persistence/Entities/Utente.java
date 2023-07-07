package Persistence.Entities;

import Persistence.Entities.organizzazione.Ente;

import java.util.Objects;

public class Utente {
    private int id_utente;
    private String titolo;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private Ente istituzione;

    public Utente(int idUtente, String titolo, String username, String password, String nome, String cognome, String email, Ente istituzione) {
        this.id_utente = idUtente;
        this.titolo = titolo;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.istituzione = istituzione;
    }

    public Utente(String titolo, String username, String password, String nome, String cognome, String email, Ente istituzione) {
        this.titolo = titolo;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.istituzione = istituzione;
    }

    public Utente() {
    }

    public Ente getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(Ente istituzione) {
        this.istituzione = istituzione;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
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
