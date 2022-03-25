package it.polimi.db2_project_20212022_fontana_gerosa.entities;

import jakarta.persistence.*;

/**
 * Entity to map an employee of the Telco company
 */
@Entity
@Table(name = "employee", schema = "db2_project")
@NamedQuery(name = "Employee.checkEmployeeCredentials", query = "SELECT e from Employee e where e.email = ?1 and e.password = ?2")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    /**
     * Email with which the Employee has been registered into  the DB
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Password used by the Employee to authenticate themselves
     */
    @Column(name = "password", nullable = false)
    private String password;

    public Employee() {

    }

    public Employee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
}
