package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Employee;

import java.util.Collection;

public class ClientEmployee {

    private int employeeId;
    private String employeeEmail;

    public ClientEmployee(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.employeeEmail = employee.getEmail();
    }
}
