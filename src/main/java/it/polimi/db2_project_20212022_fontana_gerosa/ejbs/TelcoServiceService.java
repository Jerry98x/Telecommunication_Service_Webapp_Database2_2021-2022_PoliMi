package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class TelcoServiceService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    /**
     * Gives all TelcoServices present in the DB
     * @return a collection containing all the TelcoServices
     */
    public List<TelcoService> getAllServices() {
        List<TelcoService> services = null;

        try {
            services = em.createNamedQuery("TelcoService.getAllServices", TelcoService.class).getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve ejbs");
        }

        return services;
    }

    /**
     * Gives a TelcoService given its id
     * @param serviceId id of the TelcoService to be searched
     * @return the TelcoService found
     */
    public TelcoService getServiceById(int serviceId) {
        TelcoService telcoService = null;
        try {
            telcoService = em.find(TelcoService.class, serviceId);
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve ejbs");
        }
        return telcoService;
    }

}


