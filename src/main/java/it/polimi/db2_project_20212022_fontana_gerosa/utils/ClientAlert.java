package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Alert;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClientAlert {
    private int alertId;
    private int userId;
    private String username;
    private float totalAmount_euro;
    private Date rejectionDate;
    private Time rejectionHour;

    public ClientAlert(Alert alert) {
        this.alertId = alert.getAlertId();
        this.userId = alert.getAlertId();
        this.username = alert.getUsername();
        this.totalAmount_euro = alert.getTotalAmount_euro();
        this.rejectionDate = alert.getRejectionDate();
        this.rejectionHour = alert.getRejectionHour();
    }

}
