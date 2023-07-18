package Utilities;

import java.math.BigDecimal;

public class Stats {
    private String istituzione;
    private double percentuale;

    public String getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(String istituzione) {
        this.istituzione = istituzione;
    }

    public double getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(double percentuale) {
        this.percentuale = percentuale;
    }

    @Override
    public String toString() {
        return "istituzione='" + istituzione + '\'' +
                ", percentuale=" + percentuale;
    }
}
