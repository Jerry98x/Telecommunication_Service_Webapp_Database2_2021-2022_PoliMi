package beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OptionalProduct {
    private Long optionalProductId;
    private String name;
    private int monthlyFee_euro;

    public void setOptionalProductId(Long optionalProductId) {
        this.optionalProductId = optionalProductId;
    }

    @Id
    public Long getOptionalProductId() {
        return optionalProductId;
    }
}
