package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;


@Entity
@Table(name = "order", schema = "db2_project")
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
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "servicePackageId")
    private ServicePackage servicePackage;

    @ManyToOne
    @JoinColumn(name = "validityPeriodId")
    private ValidityPeriod chosenValidityPeriod;

    @ManyToMany
    @JoinTable(name="order__optional_product",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> chosenOptionalProducts;

/*
    @OneToOne(mappedBy = "lastRejectedOrder")
    private Alert optionalAlert;

 */



    public User getUser() {
        return user;
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

    public LocalDate getConfirmationDate() {
        return confirmationDate.toLocalDate();
    }

    public LocalTime getConfirmationHour() {
        return confirmationHour.toLocalTime();
    }

    public float getTotalCost_euro() {
        return totalCost_euro;
    }

    public LocalDate getStartDate() {
        return startDate.toLocalDate();
    }

    public int getValid() {
        return valid;
    }

    public ValidityPeriod getChosenValidityPeriod() {
        return chosenValidityPeriod;
    }

    public Collection<OptionalProduct> getChosenOptionalProducts() {
        return chosenOptionalProducts;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public void setConfirmationHour(Time confirmationHour) {
        this.confirmationHour = confirmationHour;
    }

    public void setTotalCost_euro(float totalCost_euro) {
        this.totalCost_euro = totalCost_euro;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public void setChosenValidityPeriod(ValidityPeriod chosenValidityPeriod) {
        this.chosenValidityPeriod = chosenValidityPeriod;
    }

    public void setChosenOptionalProducts(Collection<OptionalProduct> chosenOptionalProducts) {
        this.chosenOptionalProducts = chosenOptionalProducts;
    }
}
