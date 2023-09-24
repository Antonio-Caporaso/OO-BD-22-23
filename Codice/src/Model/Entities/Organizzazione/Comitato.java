package Model.Entities.Organizzazione;
import Model.DAO.ComitatoDao;
import Model.DAO.OrganizzatoreDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.scene.layout.HBox;

import java.sql.SQLException;
import java.util.LinkedList;
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
        membri.addAll(comitatoDao.retreiveMembriComitato(this));
    }
    public void addMembro(Organizzatore o) throws SQLException {
        if(!(membri.contains(o))){
                ComitatoDao dao = new ComitatoDao();
                dao.addMembroComitato(o, this);
                membri.add(o);
        }
    }
    public void removeMembro(Organizzatore o) throws SQLException {
        if(membri.contains(o)) {
            ComitatoDao dao = new ComitatoDao();
            dao.removeMembroComitato(o,this);
            membri.remove(o);
        }
    }

    public ObservableList<Organizzatore> getMembri() {
        return membri;
    }

    public void setMembri(ObservableList<Organizzatore> membri) {
        membri.clear();
        this.membri = membri;
    }

    public int getId_comitato() {
        return id_comitato;
    }

    public void setId_comitato(int id_comitato) {
        this.id_comitato = id_comitato;
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
