
package Persistence.Entities.Conferenze;

import Persistence.DAO.InterventoDao;
import Persistence.Entities.partecipanti.Speaker;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

    public class Intervento extends Activity {
        private int interventoID;
        private String titolo;
        private String estratto;
        private Speaker speaker;
        private Programma programma;

        public Intervento(Timestamp orario, String estratto, Speaker speaker, Programma programma) {
            super(orario);
            this.estratto = estratto;
            this.speaker = speaker;
            this.programma=programma;
        }

        public Intervento() {
        }

        public String getTitolo() {
            return titolo;
        }

        public void setTitolo(String titolo) {
            this.titolo = titolo;
        }

        public void setSpeaker(Speaker speaker) {
            this.speaker = speaker;
        }

        public Speaker getSpeaker() {
            return speaker;
        }

        public String getEstratto() {
            return estratto;
        }

        public void setEstratto(String estratto) {
            this.estratto = estratto;
        }

        public int getInterventoID() {
            return interventoID;
        }

        public void setInterventoID(int interventoID) {
            this.interventoID = interventoID;
        }
        public Programma getProgramma() {return programma;}
        public void setProgramma(Programma programma) {this.programma = programma;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Intervento that = (Intervento) o;

            return Objects.equals(speaker, that.speaker);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), speaker);
        }

        @Override
        public String toString() {
            return titolo + ", orario=" + getOrario() +
                    ", estratto='" + estratto + ", speaker=" + speaker +
                    '.';
        }
}