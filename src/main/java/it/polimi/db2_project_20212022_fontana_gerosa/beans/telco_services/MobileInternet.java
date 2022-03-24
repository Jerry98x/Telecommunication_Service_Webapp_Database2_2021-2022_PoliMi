package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "mobile_internet", schema = "db2_project")
@PrimaryKeyJoinColumn(name = "serviceId", referencedColumnName = "serviceId")
public class MobileInternet extends TelcoService {
    @Column(name = "GBs", nullable = false)
    private int GBs;
    @Column(name = "extraGBFee_euro")
    private float extraGBFee_euro;


    @Override
    public String getDescription() {
        String description = "You get " + GBs + " GBs on your mobile device connection.";
        return description;
    }
}
