package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "validity_period", schema = "db2_project")
public class ValidityPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int validityPeriodId;

    @Column(name = "monthsOfValidity", nullable = false)
    private int monthsOfValidity;

    @Column(name = "monthlyFee(€)", nullable = false)
    private float monthlyFee_euro;

    @ManyToMany(mappedBy = "availableValidityPeriods")
    private Collection<ServicePackage> servicePackages;

    @OneToMany(mappedBy = "validityPeriod")
    private Collection<Order> orders;
}
