package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity to map an alert to represent the snapshot of a refused payment after some attempts
 */
@Entity
@Table(name = "alert", schema = "db2_project")
@NamedQuery(name = "Alert.getAllAlerts", query = "SELECT a FROM Alert a")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int alertId;

    /**
     * Id of the User who failed the payment
     */
    @Column(name = "userId", nullable = false)
    private int userId;

    /**
     * Username of the User who failed the payment
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Email of the User who failed the payment
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Amount (in euros) of the failed payment
     */
    @Column(name = "totalAmount_euro", nullable = false)
    private float totalAmount_euro;

    /**
     * Date of the rejection of the Order
     */
    @Column(name = "rejectionDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date rejectionDate;

    /**
     * Hour of the rejection of the Order
     */
    @Column(name = "rejectionHour", nullable = false)
    @Temporal(TemporalType.TIME)
    private Time rejectionHour;

    /**
     * Build an Alert starting from a failed Order and an insolvent User
     * @param user who failed the payment
     * @param order whose payment has been failed
     */
    public Alert(User user, Order order){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.totalAmount_euro = order.getTotalCost_euro();
        this.rejectionDate = Date.valueOf(LocalDate.now());
        this.rejectionHour = Time.valueOf(LocalTime.now());
    }

    public Alert() {
    }

    /**
     * Gives textual description of the alert
     * @return the String containing the description
     */
    public String getDescription(){
        return "Alert n." + alertId + " for user " + username + " (id: " + userId + ", mail: " + email + ") of " +
                totalAmount_euro + "€, raised on " + rejectionDate.toString() + " at " + rejectionHour.toString();
    }

    public int getAlertId() {
        return alertId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public float getTotalAmount_euro() {
        return totalAmount_euro;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public Time getRejectionHour() {
        return rejectionHour;
    }
}
