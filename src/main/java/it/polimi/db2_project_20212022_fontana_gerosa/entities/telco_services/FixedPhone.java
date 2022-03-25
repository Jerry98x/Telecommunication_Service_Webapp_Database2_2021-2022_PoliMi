package it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


/**
 * Entity to map a service which is taught to provide
 * calling features on a fixed device
 */
@Entity
@Table(name = "fixed_phone", schema = "db2_project")
@PrimaryKeyJoinColumn(name = "serviceId", referencedColumnName = "serviceId")
public class FixedPhone extends TelcoService {

    @Override
    public String getDescription() {
        String description = "You get unlimited calls from home.";
        return description;
    }
}
