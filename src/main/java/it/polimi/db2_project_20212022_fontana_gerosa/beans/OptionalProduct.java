package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "optional_product", schema = "db2_project")
@NamedQuery(name = "OptionalProduct.getOptionalProductsByPackageId",
        query = "SELECT o FROM OptionalProduct o, ServicePackage sp, ServicePackage.availableOptionalProducts spo WHERE " +
                "o.optionalProductId = spo.optionalProductId AND sp.servicePackageId = ?1")
public class OptionalProduct {
    @Id
    private int optionalProductId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "monthlyFee", nullable = false)
    private float monthlyFee_euro;

    @ManyToMany(mappedBy = "availableOptionalProducts")
    private Collection<ServicePackage> servicePackages;

    @ManyToMany(mappedBy = "chosenOptionalProducts")
    private Collection<Order> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
