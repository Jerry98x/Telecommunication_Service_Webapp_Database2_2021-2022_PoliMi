package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "alert", schema = "db2_project")
public class Alert {
    @Id
    private int alertId;
    /*
    @OneToOne
    @Column(name = "userId", nullable = false)
    private User insolventUser;
    @OneToOne
    @Column(name = "lastRejectedOrderId", nullable = false)
    private Order lastRejectedOrder;

     */

}
