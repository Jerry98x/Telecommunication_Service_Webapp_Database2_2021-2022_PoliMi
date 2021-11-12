package beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Boolean insolvent = false;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    public Long getUserId() {
        return userId;
    }
}
