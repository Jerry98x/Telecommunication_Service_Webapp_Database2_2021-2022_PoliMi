package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import jakarta.persistence.*;

import java.util.Collection;

@Entity @Table(name = "service")
abstract public class TelcoService {
    private Long serviceId;

    @ManyToMany(mappedBy = "services")
    private Collection<ServicePackage> servicePackages;

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Id
    public Long getServiceId() {
        return serviceId;
    }
}