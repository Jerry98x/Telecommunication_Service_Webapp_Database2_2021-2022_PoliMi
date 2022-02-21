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
