package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.util.Collection;

@Entity @Table(name = "optional_product")
public class OptionalProduct {
    private Long optionalProductId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int monthlyFee_euro;

    @ManyToMany(mappedBy = "optionalProducts")
    private Collection<ServicePackage> servicePackages;

    @ManyToOne
    @JoinColumn(name = "optionalProductId")
    private Employee creatorEmployee;

    @ManyToMany
    private Collection<Order> orders;

    public void setOptionalProductId(Long optionalProductId) {
        this.optionalProductId = optionalProductId;
    }

    @Id
    public Long getOptionalProductId() {
        return optionalProductId;
    }
}
