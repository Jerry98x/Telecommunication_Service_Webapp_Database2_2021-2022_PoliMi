package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "user")
@NamedQuery(name = "User.checkCredentials", query = "SELECT u from User u where u.email = ?1 and u.password = ?2")
@NamedQuery(name = "User.findByEmail", query = "SELECT u from User u where  u.email = ?1")
@NamedQuery(name = "User.findByUsername", query = "SELECT u from User u where  u.username = ?1")
public class User {

    @Id
    private int userId;
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

    public User(){
    }

    public User(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getInsolvent() {
        return insolvent;
    }

    public void setInsolvent(Boolean insolvent) {
        this.insolvent = insolvent;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
