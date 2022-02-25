package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mobile_phone", schema = "db2_project")
public class MobilePhone extends TelcoService {
    @Column(nullable = false)
    private int minutes;
    @Column(nullable = false)
    private int SMSs;
    private float extraMinFee_euro;
    private float extraSMSFee_euro;


    @Override
    public String getDescription() {
        String description = "You get " + minutes + " minutes and " + SMSs + " SMSs on your mobile device";
        return description;
    }
}
