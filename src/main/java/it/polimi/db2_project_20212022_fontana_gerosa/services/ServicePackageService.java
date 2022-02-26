package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
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
        /*
        if(servicePackages != null) {
            for(ServicePackage servicePackage : servicePackages){
                Collection<TelcoService> telcoServices = null;
                telcoServices = telcoServiceService.getServicesByPackage(servicePackage);
                Collection<OptionalProduct> optionalProducts = null;
                optionalProducts = optionalProductService.getOptionalProductsByPackage(servicePackage);
                if(telcoServices != null && optionalProducts != null){//TODO empty or null?
                    clientServicePackages = new ArrayList<>();
                    clientServicePackages.add(new ClientServicePackage(servicePackage, telcoServices, optionalProducts));
                }
            }
        }

         */

        return clientServicePackages;
    }
}
