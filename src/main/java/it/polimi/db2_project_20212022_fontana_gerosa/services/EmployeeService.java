package it.polimi.db2_project_20212022_fontana_gerosa.services;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa") //TODO maybe another persitence context
    private EntityManager em;
}
