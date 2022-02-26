package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.*;

@Entity
@Table(name = "mobile_phone", schema = "db2_project")
@PrimaryKeyJoinColumn(name = "serviceId", referencedColumnName = "serviceId")
public class MobilePhone extends TelcoService {
    @Column(name = "minutes", nullable = false)
    private int minutes;
    @Column(name = "SMSs", nullable = false)
    private int SMSs;
    @Column(name = "extraMinFee(€)")
    private float extraMinFee_euro;
    @Column(name = "extraSMSFee(€)")
    private float extraSMSFee_euro;


    @Override
    public String getDescription() {
        String description = "You get " + minutes + " minutes and " + SMSs + " SMSs on your mobile device";
        return description;
    }
}
