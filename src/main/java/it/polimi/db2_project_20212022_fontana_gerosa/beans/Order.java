package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Entity
@Table(name = "order")
//TODO check problems with booleans
@NamedQuery(name = "Order.getRejectedOrders", query = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.valid = false")
@NamedQuery(name = "Order.getPendingOrders", query = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.pending = true")
public class Order {
    @Id
    private int orderId;
    @Column(nullable = false)
    private LocalDate dateOfConfirmation;
    @Column(nullable = false)
    private LocalTime hourOfConfirmation;
    @Column(nullable = false)
    private float totalCost_euro;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private Boolean valid;
    @Column(nullable = false)
    private Boolean pending;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "servicePackageId")
    private ServicePackage servicePackage;

    @OneToOne(mappedBy = "lastRejectedOrder")
    private Alert optionalAlert;

    @ManyToMany
    @JoinTable(name="order__optional_product",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "optionalProductId", referencedColumnName = "optionalProductId"))
    private Collection<OptionalProduct> chosenOptionalProducts;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDateOfConfirmation() {
        return dateOfConfirmation;
    }

    public void setDateOfConfirmation(LocalDate dateOfConfirmation) {
        this.dateOfConfirmation = dateOfConfirmation;
    }

    public LocalTime getHourOfConfirmation() {
        return hourOfConfirmation;
    }

    public void setHourOfConfirmation(LocalTime hourOfConfirmation) {
        this.hourOfConfirmation = hourOfConfirmation;
    }

    public float getTotalCost_euro() {
        return totalCost_euro;
    }

    public void setTotalCost_euro(float totalCost_euro) {
        this.totalCost_euro = totalCost_euro;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean isPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }


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

    public Alert getOptionalAlert() {
        return optionalAlert;
    }

    public void setOptionalAlert(Alert optionalAlert) {
        this.optionalAlert = optionalAlert;
    }

    public Collection<OptionalProduct> getChosenOptionalProducts() {
        return chosenOptionalProducts;
    }

    public void setChosenOptionalProducts(Collection<OptionalProduct> chosenOptionalProducts) {
        this.chosenOptionalProducts = chosenOptionalProducts;
    }
}
