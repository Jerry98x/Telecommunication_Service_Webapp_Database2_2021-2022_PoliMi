package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.ValidityPeriod;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

/**
 * EJB to manage validity period entities
 */
@Stateless
public class ValidityPeriodService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    /**
     * Gives a ValidityPeriod given its id
     * @param validityPeriodId id of the ValidityPeriod to be searched
     * @return the ValidityPeriod found
     */
    public ValidityPeriod getValidityPeriodById(int validityPeriodId){
        ValidityPeriod matchingValidityPeriod = null;
        try {
            matchingValidityPeriod = em.find(ValidityPeriod.class, validityPeriodId);
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve validity period");
        }
        return matchingValidityPeriod;
    }

    /**
     * Gives all the possible ValidityPeriods in the DB
     * @return a collection of all the ValidityPeriods in the DB
     */
    public List<ValidityPeriod> getAllValidityPeriods() {
        List<ValidityPeriod> validityPeriods = null;

        try {
            validityPeriods = em.createNamedQuery("ValidityPeriod.getAllValidityPeriods", ValidityPeriod.class).
                    getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve validity periods");
        }
        return validityPeriods;
    }

}
