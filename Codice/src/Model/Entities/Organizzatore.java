package Model.Entities;

import java.util.Objects;

public class Organizzatore {
    private String cognome;
    private String email;
    private int id_organizzatore;
    private Ente istituzione;
    private String nome;
    private String titolo;

    public Organizzatore() {
    }

    public Organizzatore(String titolo, String cognome, String nome, Ente istituzione, String email) {
        this.titolo = titolo;
        this.cognome = cognome;
        this.nome = nome;
        this.istituzione = istituzione;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organizzatore that = (Organizzatore) o;

        if (id_organizzatore != that.id_organizzatore) return false;
        if (!Objects.equals(titolo, that.titolo)) return false;
        if (!Objects.equals(cognome, that.cognome)) return false;
        if (!Objects.equals(nome, that.nome)) return false;
        if (!Objects.equals(istituzione, that.istituzione)) return false;
        return Objects.equals(email, that.email);
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

    public int getId_organizzatore() {
        return id_organizzatore;
    }

    public void setId_organizzatore(int id_organizzatore) {
        this.id_organizzatore = id_organizzatore;
    }

    public Ente getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(Ente istituzione) {
        this.istituzione = istituzione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public int hashCode() {
        int result = id_organizzatore;
        result = 31 * result + (titolo != null ? titolo.hashCode() : 0);
        result = 31 * result + (cognome != null ? cognome.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (istituzione != null ? istituzione.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return cognome + " " + nome + " (" + istituzione.getSigla() + ")";
    }
}

