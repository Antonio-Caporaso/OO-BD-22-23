package Persistence.Entities.partecipanti;

import Persistence.Entities.organizzazione.Ente;

import java.util.Objects;

public class Partecipante {
    private int idPartecipante;
    private String titolo;
    protected String cognome;
    protected String nome;
    private Ente istituzione;
    private String email;

    public Partecipante() {}

    public Partecipante(String titolo, String cognome, String nome, Ente istituzione, String email) {
        this.titolo = titolo;
        this.cognome = cognome;
        this.nome = nome;
        this.istituzione = istituzione;
        this.email = email;
    }

    public int getIdPartecipante() {
        return idPartecipante;
    }

    public void setIdPartecipante(int idPartecipante) {
        this.idPartecipante = idPartecipante;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Ente getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(Ente istituzione) {
        this.istituzione = istituzione;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString(){
        return nome + " " + cognome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partecipante that = (Partecipante) o;

        if (!Objects.equals(cognome, that.cognome)) return false;
        if (!Objects.equals(nome, that.nome)) return false;
        if (!Objects.equals(istituzione, that.istituzione)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        int result = cognome != null ? cognome.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (istituzione != null ? istituzione.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
