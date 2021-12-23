package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "TelcoApp")
    private EntityManager em;

    public List<ServicePackage> getAllServicePackages(){
        List<ServicePackage> servicePackages = em.createNamedQuery("ServicePackage.getAllServicePackages", ServicePackage.class).getResultList();
        TelcoServiceService telcoServiceService = new TelcoServiceService();
        servicePackages.forEach(servicePackage -> telcoServiceService.getServices(servicePackage));

        return servicePackages;
    }
}
