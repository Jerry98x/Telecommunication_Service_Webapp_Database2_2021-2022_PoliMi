package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * Entity to map a possible validity period to be associated with a service package and then with the order containing it
 */
@Entity
@Table(name = "validity_period", schema = "db2_project")
@NamedQuery(name = "ValidityPeriod.getAllValidityPeriods", query = "SELECT v FROM ValidityPeriod v")
public class ValidityPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int validityPeriodId;

    /**
     * Months for which the ServicePackage bought is active
     * and during which payments need to be made
     */
    @Column(name = "monthsOfValidity", nullable = false)
    private int monthsOfValidity;

    /**
     * Fee (in euros) to be paid monthly to use the ServicePackage bought
     */
    @Column(name = "monthlyFee_euro", nullable = false)
    private float monthlyFee_euro;

    /**
     * All ServicePackages where the ValidityPeriod is available
     */
    @ManyToMany(mappedBy = "availableValidityPeriods")
    private Collection<ServicePackage> servicePackages;

    /**
     * All Orders where the ValidityPeriod has been chosen
     */
    @OneToMany(mappedBy = "chosenValidityPeriod")
    private Collection<Order> orders;

    public int getValidityPeriodId() {
        return validityPeriodId;
    }

    public int getMonthsOfValidity() {
        return monthsOfValidity;
    }

    public float getMonthlyFee_euro() {
        return monthlyFee_euro;
    }
}
