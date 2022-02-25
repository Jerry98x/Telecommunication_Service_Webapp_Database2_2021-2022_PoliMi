package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "employee", schema = "db2_project")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
