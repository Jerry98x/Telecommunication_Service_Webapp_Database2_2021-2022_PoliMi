package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixed_phone", schema = "db2_project")
public class FixedPhone extends TelcoService {

    @Override
    public String getDescription() {
        String description = "You get unlimited calls from home";
        return description;
    }
}
