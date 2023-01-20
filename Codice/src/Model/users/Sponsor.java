package Model.users;


public class Sponsor {

    private String nomeSponsor;
    private String indirizzo;
    private String numeroTelefono;
    private String tipoSponsor;
    private String email;

    public String getNomeSponsor() {
        return nomeSponsor;
    }
    public void setNomeSponsor(String nomeSponsor) {
        this.nomeSponsor = nomeSponsor;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    public String getTipoSponsor() {
        return tipoSponsor;
    }
    public void setTipoSponsor(String tipoSponsor) {
        this.tipoSponsor = tipoSponsor;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}