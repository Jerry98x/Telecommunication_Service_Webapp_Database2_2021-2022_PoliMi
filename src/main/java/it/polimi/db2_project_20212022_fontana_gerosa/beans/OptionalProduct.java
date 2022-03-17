package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import javax.swing.text.html.Option;
import java.util.Collection;

@Entity
@Table(name = "optional_product", schema = "db2_project")
@NamedQuery(name = "OptionalProduct.getAllOptionalProducts", query = "SELECT o FROM OptionalProduct o")
@NamedQuery(name = "OptionalProduct.getOptionalProductsByPackageId",
        query = "SELECT o FROM OptionalProduct o, ServicePackage sp, ServicePackage.availableOptionalProducts spo WHERE " +
                "o.optionalProductId = spo.optionalProductId AND sp.servicePackageId = ?1")
@NamedQuery(name = "OptionalProduct.getOptionalProductById", query = "SELECT o FROM OptionalProduct o WHERE o.optionalProductId = ?1")
public class OptionalProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionalProductId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "monthlyFee(€)", nullable = false)
    private float monthlyFee_euro;

    @ManyToMany(mappedBy = "availableOptionalProducts")
    private Collection<ServicePackage> servicePackages;

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
