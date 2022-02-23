package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "service_package", schema = "db2_project")
@NamedQuery(name = "ServicePackage.getAllServicePackages", query = "SELECT sp from ServicePackage sp")
public class ServicePackage {

    @Id
    private int servicePackageId;
    @Column
    private String name;

    @OneToMany(mappedBy = "servicePackage")
    private Collection<Order> orders;

    @ManyToMany
    @JoinTable(name="service_package__service",
        joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
        inverseJoinColumns = @JoinColumn(name = "serviceId", referencedColumnName = "serviceId"))
    private Collection<TelcoService> services;

    @ManyToMany
    @JoinTable(name="service_package__optional_product",
            joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> availableOptionalProducts;

    @ManyToMany
    @JoinTable(name = "service_package__validity_period",
            joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
            inverseJoinColumns = @JoinColumn(name = "validityPeriodId", referencedColumnName = "validityPeriodId"))
    private Collection<ValidityPeriod> availableValidityPeriods;

    public int getServicePackageId() {
        return servicePackageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<TelcoService> getServices() {
        return services;
    }

    public void setServices(Collection<TelcoService> services) {
        this.services = services;
    }

    public Collection<OptionalProduct> getAvailableOptionalProducts() {
        return availableOptionalProducts;
    }

}
