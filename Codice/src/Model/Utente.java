package Model;

import java.util.Objects;
import Model.conferenza.Conferenza;
import java.util.LinkedList;

public class Utente {
    private String titolo;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    //private String istituzione;

    public Utente(String nome, String cognome, String titolo, String username, String password, String email/*,String istituzione*/) {
        this.username = username;
        this.password = password;
        this.titolo = titolo;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        //this.istituzione = istituzione;
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

//    public String getIstituzione() {
//        return istituzione;
//    }
//
//    public void setIstituzione(String istituzione) {
//        this.istituzione = istituzione;
//    }

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
}
