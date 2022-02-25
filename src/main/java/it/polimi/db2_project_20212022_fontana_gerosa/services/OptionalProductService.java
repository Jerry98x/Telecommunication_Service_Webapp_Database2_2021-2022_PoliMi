package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class OptionalProductService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public List<OptionalProduct> getOptionalProductsByPackage(ServicePackage servicePackage){
        List<OptionalProduct> optionalProducts = null;
        try{
            optionalProducts = em.createNamedQuery("OptionalProduct.getOptionalProductsByPackageId", OptionalProduct.class).
                    setParameter(1,servicePackage.getServicePackageId()).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("couldn't retrieve products of a package");
        }
        return optionalProducts;
    }
}
