package Services;

public class Stats {
    private String istituzione;
    private int percentuale;

    public String getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(String istituzione) {
        this.istituzione = istituzione;
    }

    public int getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(int percentuale) {
        this.percentuale = percentuale;
    }

    @Override
    public String toString() {
        return  "istituzione='" + istituzione + '\'' +
                ", percentuale=" + percentuale;
    }
}
