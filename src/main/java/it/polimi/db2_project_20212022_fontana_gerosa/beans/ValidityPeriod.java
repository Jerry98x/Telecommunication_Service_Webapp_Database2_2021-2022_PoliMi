package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "validity_period", schema = "db2_project")
@NamedQuery(name = "ValidityPeriod.getAllValidityPeriods", query = "SELECT v FROM ValidityPeriod v")
public class ValidityPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int validityPeriodId;

    @Column(name = "monthsOfValidity", nullable = false)
    private int monthsOfValidity;

    @Column(name = "monthlyFee_euro", nullable = false)
    private float monthlyFee_euro;

    @ManyToMany(mappedBy = "availableValidityPeriods")
    private Collection<ServicePackage> servicePackages;

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
