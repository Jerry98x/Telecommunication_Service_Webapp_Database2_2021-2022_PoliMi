package beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Order {
    private Long orderId;
    private LocalDate dateOfConfirmation;
    private LocalTime hourOfConfirmation;
    private int totalCost_euro;
    private LocalDate startDate;
    private Boolean valid;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Id
    public Long getOrderId() {
        return orderId;
    }
}
