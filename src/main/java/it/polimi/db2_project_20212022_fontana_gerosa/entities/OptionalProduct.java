package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * Entity to map an optional product offered by the company
 * in pair with the service packages
 */
@Entity
@Table(name = "optional_product", schema = "db2_project")
@NamedQuery(name = "OptionalProduct.getAllOptionalProducts", query = "SELECT o FROM OptionalProduct o")
@NamedQuery(name = "OptionalProduct.getOptionalProductsByPackageId",
        query = "SELECT o FROM OptionalProduct o, ServicePackage sp, ServicePackage.availableOptionalProducts spo WHERE " +
                "o.optionalProductId = spo.optionalProductId AND sp.servicePackageId = ?1")
public class OptionalProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionalProductId;

    /**
     * Name of the product, may be not unique
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Fee (in euros) to be paid monthly once the order has been successfully processed
     */
    @Column(name = "monthlyFee_euro", nullable = false)
    private float monthlyFee_euro;

    /**
     * All ServicePackages where the OptionalProduct is available to be bought in pair
     */
    @ManyToMany(mappedBy = "availableOptionalProducts")
    private Collection<ServicePackage> servicePackages;

    /**
     * All Orders where the OptionalProduct has been chosen to be bought
     */
    @ManyToMany(mappedBy = "chosenOptionalProducts")
    private Collection<Order> orders;

    public int getOptionalProductId() {
        return optionalProductId;
    }

    public String getName() {
        return name;
    }

    public float getMonthlyFee_euro() {
        return monthlyFee_euro;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyFee_euro(float monthlyFee_euro) {
        this.monthlyFee_euro = monthlyFee_euro;
    }
}
