package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.util.Collection;

@Entity @Table(name = "user")
public class User {
    private Long userId;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private Boolean insolvent = false;

    @OneToMany(mappedBy = "user")
    private Collection<Order> orders;

    @OneToOne(mappedBy = "insolventUser")
    private Alert alert;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    public Long getUserId() {
        return userId;
    }
}
