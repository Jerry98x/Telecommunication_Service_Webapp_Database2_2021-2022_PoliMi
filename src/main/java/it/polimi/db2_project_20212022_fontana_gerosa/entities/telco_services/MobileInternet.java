package it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Entity to map a service which is taught to provide
 * networking features on a mobile device
 */
@Entity
@Table(name = "mobile_internet", schema = "db2_project")
@PrimaryKeyJoinColumn(name = "serviceId", referencedColumnName = "serviceId")
public class MobileInternet extends TelcoService {

    /**
     * Number of GBs offered
     */
    @Column(name = "GBs", nullable = false)
    private int GBs;

    /**
     * Fee (in euros) to be paid for each extra GB over the number offered
     */
    @Column(name = "extraGBFee_euro")
    private float extraGBFee_euro;


    @Override
    public String getDescription() {
        String description = "You get " + GBs + " GBs on your mobile device connection.";
        return description;
    }
}
