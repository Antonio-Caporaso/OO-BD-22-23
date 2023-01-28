package Model.organizzazione;

public class Sponsor {
    private int idSponsor;
    private String nome;

    public Sponsor() {
    }

    public Sponsor(String nome) {
        this.nome = nome;
    }

    public int getIdSponsor() {
        return idSponsor;
    }

    public void setIdSponsor(int idSponsor) {
        this.idSponsor = idSponsor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
