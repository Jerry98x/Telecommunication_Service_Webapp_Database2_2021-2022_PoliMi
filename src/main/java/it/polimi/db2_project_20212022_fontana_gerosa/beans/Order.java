package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;


@Entity
@Table(name = "order", schema = "db2_project")
//TODO check problems with booleans
@NamedQuery(name = "Order.getRejectedOrders", query = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.valid = 0")
@NamedQuery(name = "Order.getRejectedOrderById", query = "SELECT o FROM Order o WHERE o.orderId = ?1 AND o.valid = 0")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date confirmationDate;

    @Column(name = "hour", nullable = false)
    @Temporal(TemporalType.TIME)
    private Time confirmationHour;

    @Column(name = "totalCost(€)", nullable = false)
    private float totalCost_euro;

    @Column(name = "startDate", nullable = false)
    @Temporal(TemporalType.DATE)
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
    public int getOrderId() {
        return orderId;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public Time getConfirmationHour() {
        return confirmationHour;
    }

    public float getTotalCost_euro() {
        return totalCost_euro;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getValid() {
        return valid;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public Collection<OptionalProduct> getChosenOptionalProducts() {
        return chosenOptionalProducts;
    }

}
