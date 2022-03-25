package it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services;

import jakarta.persistence.*;

/**
 * Entity to map a service which is taught to provide
 * calling features on a mobile device
 */
@Entity
@Table(name = "mobile_phone", schema = "db2_project")
@PrimaryKeyJoinColumn(name = "serviceId", referencedColumnName = "serviceId")
public class MobilePhone extends TelcoService {

    /**
     * Number of minutes of calling offered
     */
    @Column(name = "minutes", nullable = false)
    private int minutes;

    /**
     * Number of SMSs offered
     */
    @Column(name = "SMSs", nullable = false)
    private int SMSs;

    /**
     * Fee (in euros) to be paid for each extra minute over the number offered
     */
    @Column(name = "extraMinFee_euro")
    private float extraMinFee_euro;

    /**
     * Fee (in euros) to be paid for each extra SMS over the number offered
     */
    @Column(name = "extraSMSFee_euro")
    private float extraSMSFee_euro;


    @Override
    public String getDescription() {
        String description = "You get " + minutes + " minutes and " + SMSs + " SMSs on your mobile device.";
        return description;
    }
}
