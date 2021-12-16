package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "alert")
public class Alert {
    @Id
    private int alertId;
    @OneToOne
    @Column(nullable = false)
    private User insolventUser;
    @OneToOne
    @Column(nullable = false)
    private Order lastRejectedOrder;

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getAlertId() {
        return alertId;
    }

    public User getInsolventUser() {
        return insolventUser;
    }

    public void setInsolventUser(User insolventUser) {
        this.insolventUser = insolventUser;
    }

    public Order getLastRejectedOrder() {
        return lastRejectedOrder;
    }

    public void setLastRejectedOrder(Order lastRejectedOrder) {
        this.lastRejectedOrder = lastRejectedOrder;
    }
}
