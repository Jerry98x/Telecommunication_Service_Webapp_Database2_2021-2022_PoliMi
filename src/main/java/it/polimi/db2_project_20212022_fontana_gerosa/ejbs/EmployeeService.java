package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import javax.security.auth.login.CredentialException;
import java.util.List;

/**
 * EJB to manage employee entities
 */
@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    /**
     * Checks if the Employee trying to log in is present into the DB
     * @param email to identify the Employee
     * @param password to authenticate the Employee
     * @return the Employee if found in the DB, null otherwise
     * @throws CredentialException if the credentials couldn't be verified
     */
    public Employee checkEmployeeCredentials(String email, String password) throws CredentialException {
        List<Employee> matchingEmployees;
        try {
            matchingEmployees = em.createNamedQuery("Employee.checkEmployeeCredentials", Employee.class).
                    setParameter(1, email).setParameter(2, password).getResultList();
        } catch (PersistenceException e){
            throw new CredentialException("Could not verify credentials");
        }
        if(matchingEmployees.isEmpty()){
            return null;
        }
        return matchingEmployees.get(0);
    }

}
