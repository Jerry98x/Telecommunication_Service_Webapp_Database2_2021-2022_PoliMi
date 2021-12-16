package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "validity_period")
public class ValidityPeriod {
    @Id
//    @Column(name = "validity_period_id", nullable = false)
    private int validityPeriodId;

    @Column(nullable = false)
    private int monthsOfValidity;
    @Column(nullable = false)
    private float monthlyFee_euro;

    public int getValidityPeriodId() {
        return validityPeriodId;
    }

    public void setValidityPeriodId(int validityPeriodId) {
        this.validityPeriodId = validityPeriodId;
    }

    public int getMonthsOfValidity() {
        return monthsOfValidity;
    }

    public void setMonthsOfValidity(int monthsOfValidity) {
        this.monthsOfValidity = monthsOfValidity;
    }

    public float getMonthlyFee() {
        return monthlyFee_euro;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee_euro = monthlyFee;
    }
}
