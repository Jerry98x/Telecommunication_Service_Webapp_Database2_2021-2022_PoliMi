package beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Alert {
    private Long alertId;
    private Long lastRejectedOrderId;

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    @Id
    public Long getAlertId() {
        return alertId;
    }
}
