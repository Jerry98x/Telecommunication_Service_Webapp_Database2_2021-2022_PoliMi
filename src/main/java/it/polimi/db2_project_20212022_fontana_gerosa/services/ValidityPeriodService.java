package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ValidityPeriod;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class ValidityPeriodService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

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
