package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity @Table(name = "order")
public class Order {
    private Long orderId;
    @Column(nullable = false)
    private LocalDate dateOfConfirmation;
    @Column(nullable = false)
    private LocalTime hourOfConfirmation;
    @Column(nullable = false)
    private int totalCost_euro;
    @Column(nullable = false)
    private LocalDate startDate;
    private Boolean valid;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "servicePackageId")
    private ServicePackage servicePackage;

    @OneToOne(mappedBy = "lastRejectedOrder")
    private Alert optionalAlert;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Id
    public Long getOrderId() {
        return orderId;
    }
}
