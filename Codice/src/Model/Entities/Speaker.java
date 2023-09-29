package Model.Entities;

public class Speaker {
    private Ente istituzione;
    private String titolo;
    protected String cognome;
    protected String nome;
    private String email;
    private int idSpeaker;
    public Speaker(Ente istituzione, String titolo, String cognome, String nome, int idSpeaker) {
        this.istituzione = istituzione;
        this.titolo = titolo;
        this.cognome = cognome;
        this.nome = nome;
        this.idSpeaker = idSpeaker;
    }

    public Speaker(){}

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

    public int getIdSpeaker() {
        return idSpeaker;
    }

    public void setIdSpeaker(int idSpeaker) {
        this.idSpeaker = idSpeaker;
    }

    @Override
    public String toString() {
        return cognome + " " + nome;
    }
}
