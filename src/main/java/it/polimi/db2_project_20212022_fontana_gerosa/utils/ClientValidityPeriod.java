package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.ValidityPeriod;

public class ClientValidityPeriod {
    private int validityPeriodId;
    private int monthsOfValidity;
    private float monthlyFee_euro;

    public ClientValidityPeriod(ValidityPeriod validityPeriod){
        this.validityPeriodId = validityPeriod.getValidityPeriodId();
        this.monthsOfValidity = validityPeriod.getMonthsOfValidity();
        this.monthlyFee_euro = validityPeriod.getMonthlyFee_euro();
    }
}
