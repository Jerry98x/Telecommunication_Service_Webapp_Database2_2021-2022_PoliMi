package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "service_package")
public class ServicePackage {

    @Id
    private int servicePackageId;
    @Column
    private String name;

    @OneToMany(mappedBy = "servicePackage")
    private Collection<Order> orders;

    @ManyToMany
    private Collection<TelcoService> services;

    @ManyToMany
    private Collection<OptionalProduct> availableOptionalProducts;

    public void setServicePackageId(int servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    public int getServicePackageId() {
        return servicePackageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
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

    public void setAvailableOptionalProducts(Collection<OptionalProduct> availableOptionalProducts) {
        this.availableOptionalProducts = availableOptionalProducts;
    }
}
