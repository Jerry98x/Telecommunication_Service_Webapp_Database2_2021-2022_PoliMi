package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

/**
 * EJB to manage optional product entities
 */
@Stateless
public class OptionalProductService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    /**
     * Gives an OptionalProduct given its id
     * @param optionalProductId id used to search for the OptionalProduct
     * @return OptionalProduct corresponding to the id
     */
    public OptionalProduct getOptionalProductById(int optionalProductId){
        OptionalProduct matchingOptionalProduct = null;
        try {
            matchingOptionalProduct = em.find(OptionalProduct.class, optionalProductId);
        }
        catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve product");
        }
        return matchingOptionalProduct;
    }

    /**
     * Gives a collection of all possible OptionalProducts
     * @return collection of all OptionalProducts found in the DB
     */
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

    /**
     * Creates a new OptionalProduct in the DB
     * @param optionalProduct OptionalProduct to be added to the DB
     */
    public void insertOptionalProduct(OptionalProduct optionalProduct) {
        try {
            em.persist(optionalProduct);
            em.flush();
        }
        catch (PersistenceException e) {
            throw new PersistenceException("Couldn't create optional product");
        }

    }
}
