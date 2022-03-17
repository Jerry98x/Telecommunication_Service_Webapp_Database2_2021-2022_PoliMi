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
@NamedQuery(name = "Order.getOrdersByUserId", query = "SELECT o from Order o WHERE o.user.userId = ?1")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "creationHour", nullable = false)
    @Temporal(TemporalType.TIME)
    private Time creationHour;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicePackageId")
    private ServicePackage servicePackage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "validityPeriodId")
    private ValidityPeriod chosenValidityPeriod;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="order__optional_product",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> chosenOptionalProducts;


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

    public LocalDate getCreationDate() {
        return creationDate.toLocalDate();
    }

    public LocalTime getCreationHour() {
        return creationHour.toLocalTime();
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

    public void setCreationDate(LocalDate confirmationDate) {
        this.creationDate = Date.valueOf(confirmationDate);
    }

    public void setCreationHour(LocalTime confirmationHour) {
        this.creationHour = Time.valueOf(confirmationHour);
    }

    public void setTotalCost_euro(float totalCost_euro) {
        this.totalCost_euro = totalCost_euro;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Date.valueOf(startDate);
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
