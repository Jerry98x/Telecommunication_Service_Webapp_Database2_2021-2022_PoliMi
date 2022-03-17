package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "alert", schema = "db2_project")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int alertId;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "totalAmount", nullable = false)
    private float totalAmount;

    @Column(name = "rejectionDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date rejectionDate;

    @Column(name = "rejectionHour", nullable = false)
    @Temporal(TemporalType.TIME)
    private Time rejectionHour;

    public Alert(User user, Order order){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.totalAmount = order.getTotalCost_euro();
        this.rejectionDate = Date.valueOf(LocalDate.now());
        this.rejectionHour = Time.valueOf(LocalTime.now());
    }

    public Alert() {
    }
}
