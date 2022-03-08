package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class TelcoServiceService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public List<TelcoService> getServicesByPackage(ServicePackage servicePackage) {
        List<TelcoService> telcoServices = null;
        try {
            telcoServices = em.createNamedQuery("TelcoService.getServicesByPackageId", TelcoService.class).
                    setParameter(1,servicePackage.getServicePackageId()).getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve services of a package");
        }
        return telcoServices;
    }

    public List<TelcoService> getAllServices() {
        List<TelcoService> services = null;

        try {
            services = em.createNamedQuery("TelcoService.getAllServices", TelcoService.class).getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve services");
        }

        return services;
    }
}


