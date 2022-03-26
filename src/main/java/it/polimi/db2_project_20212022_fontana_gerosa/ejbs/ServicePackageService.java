package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientServicePackage;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * EJB to manage service package entities
 */
@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    /**
     * Gives a collection of ClientServicePackage containing only the info useful to the client for all ServicePackages in the DB
     * @return a collection of ClientServicePackage of all ServicePackages in the DB
     */
    public List<ClientServicePackage> getAllClientServicePackages() {
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

    /**
     * Gives a ServicePackage given its id
     * @param servicePackageId id of the ServicePackage to be searched
     * @return the ServicePackage found
     */
    public ServicePackage getServicePackageById(int servicePackageId) {
        ServicePackage matchingServicePackage;
        try{
            matchingServicePackage = em.find(ServicePackage.class, servicePackageId);
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve matching package");
        }
        return matchingServicePackage;
    }

    /**
     * Create a new ServicePackage in the DB
     * @param servicePackage the ServicePackage to be added to the DB
     */
    public void insertServicePackage(ServicePackage servicePackage) {
        try {
            em.persist(servicePackage);
            em.flush();
        }
        catch (PersistenceException e) {
            throw new PersistenceException("Couldn't create service package");
        }

    }

    /**
     * Gives a ServicePackage associated to an Order given the id of this last one
     * @param orderId id of the Order to be searched
     * @return the ServicePackage found
     */
    public ServicePackage getServicePackageByOrderId(int orderId){
        List<ServicePackage> servicePackages = null;
        try{
            servicePackages = em.createNamedQuery("ServicePackage.getServicePackageByOrderId", ServicePackage.class).
            setParameter(1, orderId).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve matching package");
        }
        if(servicePackages.isEmpty()){
            return null;
        }
        return servicePackages.get(0);
    }
}
