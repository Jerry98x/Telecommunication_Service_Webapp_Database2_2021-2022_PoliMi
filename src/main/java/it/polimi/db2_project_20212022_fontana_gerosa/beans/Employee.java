package it.polimi.db2_project_20212022_fontana_gerosa.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Collection;

@Entity @Table(name = "employee")
public class Employee {
    private Long employeeId;
    private String email;
    private String password;

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @OneToMany (mappedBy = "creatorEmployee")
    private Collection<OptionalProduct> createdOptionalProducts;

    @OneToMany (mappedBy = "creatorEmployee")
    private Collection<ServicePackage> createdServicePackages;

    @Id
    public Long getEmployeeId() {
        return employeeId;
    }
}
