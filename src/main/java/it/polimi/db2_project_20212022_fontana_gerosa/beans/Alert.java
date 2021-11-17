package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

@Entity @Table(name = "alert")
public class Alert {
    private Long alertId;
    @OneToOne
    @Column(nullable = false)
    private User insolventUser;
    @OneToOne
    @Column(nullable = false)
    private Order lastRejectedOrder;

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    @Id
    public Long getAlertId() {
        return alertId;
    }
}
