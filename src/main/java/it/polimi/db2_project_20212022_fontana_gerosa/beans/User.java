package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.Collection;

@Entity
@Table(name = "user", schema = "db2_project")
@NamedQuery(name = "User.checkCredentials", query = "SELECT u from User u where u.email = ?1 and u.password = ?2")
@NamedQuery(name = "User.findById", query = "SELECT u from User u where u.userId = ?1")
@NamedQuery(name = "User.findByEmail", query = "SELECT u from User u where  u.email = ?1")
@NamedQuery(name = "User.findByUsername", query = "SELECT u from User u where  u.username = ?1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
//    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "insolvent", nullable = false)
    private int insolvent = 0;

    @Column(name = "numOfFailedPayments", nullable = false)
    private int numOfFailedPayments = 0;

    @OneToMany(mappedBy = "user")
    private Collection<Order> orders;


    public User(){
    }

    public User(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInsolvent() {
        return insolvent;
    }

    public void setInsolvent(int insolvent) {
        this.insolvent = insolvent;
    }

    public int getNumOfFailedPayments() {
        return numOfFailedPayments;
    }

    public void setNumOfFailedPayments(int numOfFailedPayments) {
        this.numOfFailedPayments = numOfFailedPayments;
    }
}
