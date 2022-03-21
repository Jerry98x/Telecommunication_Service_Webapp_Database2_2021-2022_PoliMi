package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ValidityPeriod;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import it.polimi.db2_project_20212022_fontana_gerosa.controllers.CreateServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientServicePackage;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;
    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/TelcoServiceService")
    private TelcoServiceService telcoServiceService = new TelcoServiceService();
    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/OptionalProductService")
    private OptionalProductService optionalProductService = new OptionalProductService();

    public List<ClientServicePackage> getAllClientServicePackages(){
        List<ClientServicePackage> clientServicePackages = null;
        List<ServicePackage> servicePackages = null;
        try {
            servicePackages = em.createNamedQuery("ServicePackage.getAllServicePackages", ServicePackage.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("couldn't retrieve packages");
        }
        if(servicePackages != null){
            clientServicePackages = new ArrayList<>();
            List<ClientServicePackage> finalClientServicePackages = clientServicePackages;
            servicePackages.forEach(servicePackage -> finalClientServicePackages.add(new ClientServicePackage(servicePackage)));
        }
        return clientServicePackages;
    }

    public ServicePackage getServicePackageById(int servicePackageId){
        ServicePackage matchingServicePackage;
        try{
            matchingServicePackage = em.find(ServicePackage.class, servicePackageId);
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve matching package");
        }
        return matchingServicePackage;
    }

    public void insertServicePackage(ServicePackage servicePackage) {
        try {
            em.persist(servicePackage);
            em.flush();
        }
        catch (PersistenceException e) {
            throw new PersistenceException("Couldn't create service package");
        }

    }
}
