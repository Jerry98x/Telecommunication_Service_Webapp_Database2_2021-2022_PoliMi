package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;


@Entity
@Table(name = "order", schema = "db2_project")
//TODO check problems with booleans
@NamedQuery(name = "Order.getRejectedOrders", query = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.valid = 0")
public class Order {
    @Id
    private int orderId;
    @Column(name = "date", nullable = false)
    private Date dateOfConfirmation;
    @Column(name = "hour", nullable = false)
    private Time hourOfConfirmation;
    @Column(name = "totalCost(€)", nullable = false)
    private float totalCost_euro;
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Column(name = "valid", nullable = false)
    private int valid;

    @ManyToOne
    @JoinColumn(name = "validityPeriodId")
    private ValidityPeriod validityPeriod;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "servicePackageId")
    private ServicePackage servicePackage;
/*
    @OneToOne(mappedBy = "lastRejectedOrder")
    private Alert optionalAlert;

 */

    @ManyToMany
    @JoinTable(name="order__optional_product",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> chosenOptionalProducts;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

}
