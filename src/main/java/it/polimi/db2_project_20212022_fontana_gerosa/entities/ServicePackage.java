package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services.TelcoService;
import jakarta.persistence.*;


import java.util.Collection;

/**
 * Entity to map a service package offered by the Telco company
 */
@Entity
@Table(name = "service_package", schema = "db2_project")
@NamedQuery(name = "ServicePackage.getAllServicePackages", query = "SELECT sp from ServicePackage sp")
@NamedQuery(name = "ServicePackage.getServicePackageByOrderId", query = "SELECT s FROM ServicePackage s, Order o" +
        " WHERE s.servicePackageId = o.servicePackage.servicePackageId AND o.orderId = ?1")
public class ServicePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int servicePackageId;

    /**
     * Name of the ServicePackage, may be not unique
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * All the Orders where the ServicePackage has been bought
     */
    @OneToMany(mappedBy = "servicePackage")
    private Collection<Order> orders;

    /**
     * All TelcoServices offered by the ServicePackage
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="service_package__service",
        joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
        inverseJoinColumns = @JoinColumn(name = "serviceId", referencedColumnName = "serviceId"))
    private Collection<TelcoService> services;

    /**
     * ALl OptionalProducts available to be bought in pair with the ServicePackage
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="service_package__optional_product",
            joinColumns = @JoinColumn(name = "servicePackageId", referencedColumnName = "servicePackageId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> availableOptionalProducts;

    /**
     * All ValidityPeriods available to decide the payments for the ServicePackage
     */
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

    public void setAvailableOptionalProducts(Collection<OptionalProduct> availableOptionalProducts) {
        this.availableOptionalProducts = availableOptionalProducts;
    }

    public void setAvailableValidityPeriods(Collection<ValidityPeriod> availableValidityPeriods) {
        this.availableValidityPeriods = availableValidityPeriods;
    }

    public Collection<OptionalProduct> getAvailableOptionalProducts() {
        return availableOptionalProducts;
    }

    public Collection<ValidityPeriod> getAvailableValidityPeriods() {
        return availableValidityPeriods;
    }
}
