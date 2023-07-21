package Model.Entities.organizzazione;
import Model.DAO.ComitatoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Objects;

public class Comitato {
    private int id_comitato;
    private String tipologia;
    private ObservableList<Organizzatore> membri;

    public Comitato() {
        membri = FXCollections.observableArrayList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comitato comitato = (Comitato) o;

        if (id_comitato != comitato.id_comitato) return false;
        return Objects.equals(tipologia, comitato.tipologia);
    }
    public void loadMembri() throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        membri.clear();
        membri.addAll(comitatoDao.retreiveMembryComitato(this));
    }
    public void addMembro(Organizzatore o){
        if(!(membri.contains(o))){
            membri.add(o);
        }
    }
    public void removeMembro(Organizzatore o){
        if(membri.contains(o))
            membri.remove(o);
    }

    public ObservableList<Organizzatore> getMembri() {
        return membri;
    }

    public int getId_comitato() {
        return id_comitato;
    }

    public void setId_comitato(int id_comitato) {
        this.id_comitato = id_comitato;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public int hashCode() {
        int result = id_comitato;
        result = 31 * result + (tipologia != null ? tipologia.hashCode() : 0);
        return result;
    }
}
