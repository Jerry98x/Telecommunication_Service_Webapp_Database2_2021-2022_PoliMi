package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import jakarta.persistence.*;

import java.util.Collection;

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

    @ManyToMany(mappedBy = "services")
    private Collection<ServicePackage> servicePackages;

    public abstract String getDescription();
}
