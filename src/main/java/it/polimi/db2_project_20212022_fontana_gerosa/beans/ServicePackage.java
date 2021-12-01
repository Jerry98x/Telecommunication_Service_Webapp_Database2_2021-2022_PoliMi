package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.persistence.*;

import java.util.Collection;

@Entity @Table(name = "service_package")
public class ServicePackage {
    private Long servicePackageId;
    private String name;
    @Column(nullable = false)
    private int monthsOfValidity;
    @Column(nullable = false)
    private int monthlyFee_euro;

    @OneToMany(mappedBy = "servicePackage")
    private Collection<Order> orders;

    @ManyToMany
    private Collection<TelcoService> services;

    @ManyToMany
    private Collection<OptionalProduct> optionalProducts;

    @ManyToOne
    @JoinColumn(name = "servicePackageId")
    private Employee creatorEmployee;

    public void setServicePackageId(Long servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    @Id
    public Long getServicePackageId() {
        return servicePackageId;
    }
}
