package Model.partecipanti;

import java.util.Objects;

public class Partecipante {
    private String titolo;
    private String cognome;
    private String nome;
    private String istituzione;
    private String email;

    public Partecipante() {}

    public Partecipante(String titolo, String cognome, String nome, String istituzione, String email) {
        this.titolo = titolo;
        this.cognome = cognome;
        this.nome = nome;
        this.istituzione = istituzione;
        this.email = email;
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

    public String getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(String istituzione) {
        this.istituzione = istituzione;
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
