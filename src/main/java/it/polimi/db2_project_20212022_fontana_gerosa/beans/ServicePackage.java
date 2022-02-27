package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "service_package", schema = "db2_project")
@NamedQuery(name = "ServicePackage.getAllServicePackages", query = "SELECT sp from ServicePackage sp")
@NamedQuery(name = "ServicePackage.getServicePackageById", query = "SELECT sp from ServicePackage sp where sp.servicePackageId = ?1")
public class ServicePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int servicePackageId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "servicePackage")
    private Collection<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="service_package__service",
        joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
        inverseJoinColumns = @JoinColumn(name = "serviceId", referencedColumnName = "serviceId"))
    private Collection<TelcoService> services;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="service_package__optional_product",
            joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> availableOptionalProducts;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public Collection<ValidityPeriod> getAvailableValidityPeriods() {
        return availableValidityPeriods;
    }
}
