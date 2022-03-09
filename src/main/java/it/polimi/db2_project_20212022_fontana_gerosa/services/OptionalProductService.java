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

    public OptionalProduct getOptionalProductById(int optionalProductId){
        List<OptionalProduct> matchingOptionalProducts = null;
        try {
            matchingOptionalProducts = em.createNamedQuery("OptionalProduct.getOptionalProductById", OptionalProduct.class).
                    setParameter(1,optionalProductId).getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve product");
        }
        if(matchingOptionalProducts.isEmpty()){
            return null;
        }
        return matchingOptionalProducts.get(0);
    }

    public List<OptionalProduct> getOptionalProductsByPackage(ServicePackage servicePackage){
        List<OptionalProduct> matchingOptionalProducts = null;
        try {
            matchingOptionalProducts = em.createNamedQuery("OptionalProduct.getOptionalProductsByPackageId", OptionalProduct.class).
                    setParameter(1,servicePackage.getServicePackageId()).getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve products of a package");
        }
        return matchingOptionalProducts;
    }

    public List<OptionalProduct> getAllOptionalProducts() {
        List<OptionalProduct> allOptionalProducts = null;
        try {
            allOptionalProducts = em.createNamedQuery("OptionalProduct.getAllOptionalProducts", OptionalProduct.class).
                    getResultList();
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve optional products");
        }
        return allOptionalProducts;
    }
}
