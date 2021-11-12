package beans.services;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
abstract public class Service {
    private Long serviceId;

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Id
    public Long getServiceId() {
        return serviceId;
    }
}
