package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * Entity to map a user registered into the DB of the Telco company
 */
@Entity
@Table(name = "user", schema = "db2_project")
@NamedQuery(name = "User.checkCredentials", query = "SELECT u from User u where u.email = ?1 and u.password = ?2")
@NamedQuery(name = "User.findByEmail", query = "SELECT u from User u where  u.email = ?1")
@NamedQuery(name = "User.findByUsername", query = "SELECT u from User u where  u.username = ?1")
@NamedQuery(name = "User.getAllInsolventUsers", query = "SELECT u FROM User u WHERE u.insolvent = 1")
@NamedQuery(name = "User.getUserByOrderId", query = "SELECT u FROM User u, Order o WHERE u.userId = o.user.userId AND o.orderId = ?1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * Username chosen by the User to identify themselves
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Email with which the User has registered themselves
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * Password used by the User to authenticate themselves
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Value to represent the User being insolvent
     * (ie: having rejected Orders due to failed payments):
     * 0 = not insolvent (all the Orders have been sucessfully processed and paid)
     * 1 = insolvent (there is at least 1 Order whose payment has failed, thus it is rejected)
     *
     * NB: even though it represents a binary value the type "int" has been chosen to allow
     * an easier mapping into the DB (the constraints on the values are manually checked when assigned)
     */
    @Column(name = "insolvent", nullable = false)
    private int insolvent = 0;

    /**
     * Number of payments failed by the User
     *
     * NB: it increases while the User is insolvent,
     * when they are no more insolvent it is reset to the number of payments which have not caused an Alert yet
     * (eg: 3 failed payments to raise an Alert, User insolvent with 4 failed payments.
     * They successfully close all the pending Orders, the attribute is reset to 4%3 = 1)
     */
    @Column(name = "numOfFailedPayments", nullable = false)
    private int numOfFailedPayments = 0;

    /**
     * All Orders made by the User (both valid and not)
     */
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

    /**
     * Gives textual description of the User
     * @return a String containing the description
     */
    public String getDescription(){
        return "User " + username + " (id: " + userId + "), with mail " + email + " and " + numOfFailedPayments + " failed payments";
    }
}
