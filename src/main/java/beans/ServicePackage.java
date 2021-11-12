package beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ServicePackage {
    private Long servicePackageId;
    private String name;
    private int monthsOfValidity;
    private int monthlyFee_euro;

    public void setServicePackageId(Long servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    @Id
    public Long getServicePackageId() {
        return servicePackageId;
    }
}
