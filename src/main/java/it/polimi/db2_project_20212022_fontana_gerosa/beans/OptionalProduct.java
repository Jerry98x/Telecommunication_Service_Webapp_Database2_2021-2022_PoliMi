package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "optional_product")
public class OptionalProduct {
    @Id
    private int optionalProductId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int monthlyFee_euro;

    @ManyToMany(mappedBy = "availableOptionalProducts")
    private Collection<ServicePackage> servicePackages;

    @ManyToOne
    @JoinColumn(name = "optionalProductId")
    private Employee creatorEmployee;

    @ManyToMany
    private Collection<Order> orders;

    public void setOptionalProductId(int optionalProductId) {
        this.optionalProductId = optionalProductId;
    }


    public int getOptionalProductId() {
        return optionalProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonthlyFee_euro() {
        return monthlyFee_euro;
    }

    public void setMonthlyFee_euro(int monthlyFee_euro) {
        this.monthlyFee_euro = monthlyFee_euro;
    }

    public Collection<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(Collection<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
    }

    public Employee getCreatorEmployee() {
        return creatorEmployee;
    }

    public void setCreatorEmployee(Employee creatorEmployee) {
        this.creatorEmployee = creatorEmployee;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }
}
