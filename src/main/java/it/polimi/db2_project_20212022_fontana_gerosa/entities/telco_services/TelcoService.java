package it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import jakarta.persistence.*;

import java.util.Collection;

/**
 * Super-class entity to map the most generalized description
 * of a service offered by the Telco company
 */
@Entity
@Table(name = "service", schema = "db2_project")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "TelcoService.getAllServices", query = "SELECT s FROM TelcoService s")
@NamedQuery(name = "TelcoService.getServicesByPackageId",
        query = "SELECT s FROM TelcoService s, ServicePackage sp, ServicePackage.services sps WHERE " +
                "s.serviceId = sps.serviceId AND sp.servicePackageId = ?1")
abstract public class TelcoService {
    @Id
    private int serviceId;

    /**
     * All ServicePackages where a TelcoService is offered
     */
    @ManyToMany(mappedBy = "services")
    private Collection<ServicePackage> servicePackages;

    public int getServiceId() {
        return serviceId;
    }

    /**
     * Gives a textual description of the TelcoService
     * @return a String containing the description
     */
    public abstract String getDescription();
}
