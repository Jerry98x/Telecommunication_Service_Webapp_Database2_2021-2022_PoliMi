package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * Entity to map an order made to the Telco company by a user
 */
@Entity
@Table(name = "order", schema = "db2_project")
@NamedQuery(name = "Order.getAllRejectedOrders", query = "SELECT o FROM Order o WHERE o.valid = 0")
@NamedQuery(name = "Order.getRejectedOrders", query = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.valid = 0")
@NamedQuery(name = "Order.getRejectedOrderById", query = "SELECT o FROM Order o WHERE o.orderId = ?1 AND o.valid = 0")
@NamedQuery(name = "Order.getOrdersByUserId", query = "SELECT o from Order o WHERE o.user.userId = ?1")
@NamedQuery(name = "Order.getAllRejectedOrders", query = "SELECT o FROM Order o WHERE o.valid = 0")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    /**
     * Date of creation of the Order
     */
    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    /**
     * Hour of creation of the Order
     */
    @Column(name = "creationHour", nullable = false)
    @Temporal(TemporalType.TIME)
    private Time creationHour;

    /**
     * Total cost (in euros) to be paid (monthly) once the Order has been successfully processed,
     * startin from the StartDate
     */
    @Column(name = "totalCost_euro", nullable = false)
    private float totalCost_euro;

    /**
     * Date (chosen by the User who has made the Order) from when the monthly payments need to start
     */
    @Column(name = "startDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * Value to represent the validity of the Order:
     * 0 = rejected (caused by a failed payment)
     * 1 = accepted (caused by a successful payment)
     *
     * NB: even though it represents a binary value the type "int" has been chosen to allow
     * an easier mapping into the DB (the constraints on the values are manually checked when assigned)
     */
    @Column(name = "valid", nullable = false)
    private int valid;

    /**
     * Total amount of OptionalProducts chosen in the Order
     */
    @Column(name = "amountOptionalProducts", nullable = false)
    private int amountOptionalProducts;

    /**
     * User who made the Order
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    /**
     * ServicePackage associated with the Order
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicePackageId")
    private ServicePackage servicePackage;

    /**
     * ValidityPeriod chosen by the User to made the payments
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "validityPeriodId")
    private ValidityPeriod chosenValidityPeriod;

    /**
     * All OptionalProducts chosen by the User tobe bought with the ServicePackage
     */
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

    public Date getCreationDate2() {
        return creationDate;
    }

    public LocalTime getCreationHour() {
        return creationHour.toLocalTime();
    }

    public Time getCreationHour2() {
        return creationHour;
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

    public void setAmountOptionalProducts(int amountOptionalProducts) {
        this.amountOptionalProducts = amountOptionalProducts;
    }

    public void setChosenValidityPeriod(ValidityPeriod chosenValidityPeriod) {
        this.chosenValidityPeriod = chosenValidityPeriod;
    }

    public void setChosenOptionalProducts(Collection<OptionalProduct> chosenOptionalProducts) {
        this.chosenOptionalProducts = chosenOptionalProducts;
    }
}
