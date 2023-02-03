package Model.organizzazione;

import Exceptions.ExistingMemberException;

import java.util.Objects;

public class Organizzatore {
    private String titolo;
    private String cognome;
    private String nome;
    private String istituzione;
    private String email;
    private Comitato appartieneA;

    public Organizzatore() {
    }

    public Organizzatore(String titolo, String cognome, String nome, String istituzione, String email, Comitato appartieneA) {
        this.titolo = titolo;
        this.cognome = cognome;
        this.nome = nome;
        this.istituzione = istituzione;
        this.email = email;
        this.appartieneA = appartieneA;
    }

    public void setAppartieneA(Comitato appartieneA) throws ExistingMemberException {
        this.appartieneA = appartieneA;
        appartieneA.add(this);
    }
    public Comitato getAppartieneA(){
        return appartieneA;
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

        Organizzatore that = (Organizzatore) o;

        if (!Objects.equals(cognome, that.cognome)) return false;
        if (!Objects.equals(nome, that.nome)) return false;
        if (!Objects.equals(istituzione, that.istituzione)) return false;
        if (!Objects.equals(email, that.email)) return false;
        return Objects.equals(appartieneA, that.appartieneA);
    }

    @Override
    public int hashCode() {
        int result = cognome != null ? cognome.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (istituzione != null ? istituzione.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (appartieneA != null ? appartieneA.hashCode() : 0);
        return result;
    }
}