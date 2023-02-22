package Model.Conferenze;

import java.time.Duration;
import java.util.Objects;

public class Proiezione extends EventoSociale {
    private int proiezioneID;
    private String nome;
    private Duration durata;
    private String genere;
    private String anno;
    private String produttore;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Duration getDurata() {
        return durata;
    }

    public void setDurata(Duration durata) {
        this.durata = durata;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getProduttore() {
        return produttore;
    }

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proiezione that = (Proiezione) o;

        if (!Objects.equals(nome, that.nome)) return false;
        return Objects.equals(anno, that.anno);
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (anno != null ? anno.hashCode() : 0);
        return result;
    }
}
