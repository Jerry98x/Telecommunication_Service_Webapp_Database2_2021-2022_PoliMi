package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.ValidityPeriod;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.mvs.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * EJB to manage all the materialized view entities
 */
@Stateless
public class MVService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService = new ValidityPeriodService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OptionalProductService")
    private OptionalProductService optionalProductService = new OptionalProductService();

    /**
     * Gives the descriptions of each row of the corresponding materialized view
     * @return a collection of Strings containing the descriptions of each row
     */
    public List<String> getAllAvgAmountOpPerSpDescriptions(){
        List<MVAvgAmountOpPerSp> mvResults = null;
        List<String> descriptions = new ArrayList<>();
        try {
            mvResults = em.createNamedQuery("MVAvgAmountOpPerSp.getAllAvgAmountOpPerSp", MVAvgAmountOpPerSp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVAvgAmountOpPerSp results");
        }
        if(mvResults != null){
            mvResults.forEach(mv -> descriptions.add(getAvgAmountOpPerSpDescription(mv)));
        }
        return descriptions;
    }

    /**
     * Gives the String containing the description given the entity mapping the row of the materialized view
     * @param mv entity representing a single row
     * @return a String containing the textual description of the row
     */
    private String getAvgAmountOpPerSpDescription(MVAvgAmountOpPerSp mv) {
        ServicePackage servicePackage = null;
        try {
            servicePackage = servicePackageService.getServicePackageById(mv.getServicePackageId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve service package");
        }
        if (servicePackage != null) {
            return "Service package " + servicePackage.getName() + " (id: " + servicePackage.getServicePackageId() + ") sold with " + mv.getAvgAmountOptionalProducts() + " optional products on average";
        } else {
            return "";
        }
    }

    /**
     * Gives the descriptions of each row of the corresponding materialized view
     * @return a collection of Strings containing the descriptions of each row
     */
    public List<String> getAllTotalPurchasesPerSpDescriptions(){
        List<MVTotalPurchasesPerSp> mvResults = null;
        List<String> descriptions = new ArrayList<>();
        try {
            mvResults = em.createNamedQuery("MVTotalPurchasesPerSp.getAllTotalPurchasesPerSp", MVTotalPurchasesPerSp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalPurchasesPerSp results");
        }
        if(mvResults != null){
            mvResults.forEach(mv -> descriptions.add(getTotalPurchasesPerSpDescription(mv)));
        }
        return descriptions;
    }

    /**
     * Gives the String containing the description given the entity mapping the row of the materialized view
     * @param mv entity representing a single row
     * @return a String containing the textual description of the row
     */
    private String getTotalPurchasesPerSpDescription(MVTotalPurchasesPerSp mv){
        ServicePackage servicePackage = null;
        try {
            servicePackage = servicePackageService.getServicePackageById(mv.getServicePackageId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve service package");
        }
        if (servicePackage != null) {
            return "Service package " + servicePackage.getName() + " (id: " + servicePackage.getServicePackageId() +
                    ") has been sold " + mv.getTotalPurchases() + " times";
        } else {
            return "";
        }
    }

    /**
     * Gives the descriptions of each row of the corresponding materialized view
     * @return a collection of Strings containing the descriptions of each row
     */
    public List<String> getAllTotalPurchasesPerSpAndVpDescriptions(){
        List<MVTotalPurchasesPerSpAndVp> mvResults = null;
        List<String> descriptions = new ArrayList<>();
        try {
            mvResults = em.createNamedQuery("MVTotalPurchasesPerSpAndVp.getAllTotalPurchasesPerSpAndVp", MVTotalPurchasesPerSpAndVp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalPurchasesPerSpAndVp results");
        }
        if(mvResults != null){
            mvResults.forEach(mv -> descriptions.add(getTotalPurchasesPerSpAndVpDescription(mv)));
        }
        return descriptions;
    }

    /**
     * Gives the String containing the description given the entity mapping the row of the materialized view
     * @param mv entity representing a single row
     * @return a String containing the textual description of the row
     */
    private String getTotalPurchasesPerSpAndVpDescription(MVTotalPurchasesPerSpAndVp mv){
        ServicePackage servicePackage = null;
        ValidityPeriod validityPeriod = null;
        try {
            servicePackage = servicePackageService.getServicePackageById(mv.getServicePackageId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve service package");
        }
        try {
            validityPeriod = validityPeriodService.getValidityPeriodById(mv.getValidityPeriodId());
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve validity period");
        }
        if (servicePackage != null && validityPeriod != null) {
            return "Service package " + servicePackage.getName() + " (id: " + servicePackage.getServicePackageId() + ") has been sold with " +
                    "validity period of " + validityPeriod.getMonthlyFee_euro() + "€/month for " + validityPeriod.getMonthsOfValidity() + " months (id: " +
                    validityPeriod.getValidityPeriodId() + ") "
                    + mv.getTotalPurchases() + " times";
        } else {
            return "";
        }
    }

    /**
     * Gives the descriptions of each row of the corresponding materialized view
     * @return a collection of Strings containing the descriptions of each row
     */
    public List<String> getAllTotalValuePerSpDescriptions(){
        List<MVTotalValuePerSp> mvResults = null;
        List<String> descriptions = new ArrayList<>();
        try {
            mvResults = em.createNamedQuery("MVTotalValuePerSp.getAllTotalValuePerSp", MVTotalValuePerSp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalValuePerSp results");
        }
        if(mvResults != null){
            mvResults.forEach(mv -> descriptions.add(getTotalValuePerSpDescription(mv)));
        }
        return descriptions;
    }

    /**
     * Gives the String containing the description given the entity mapping the row of the materialized view
     * @param mv entity representing a single row
     * @return a String containing the textual description of the row
     */
    private String getTotalValuePerSpDescription(MVTotalValuePerSp mv){
        ServicePackage servicePackage = null;
        try {
            servicePackage = servicePackageService.getServicePackageById(mv.getServicePackageId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve service package");
        }
        if (servicePackage != null) {
            return "Service package " + servicePackage.getName() + " (id: " + servicePackage.getServicePackageId() +
                    ") has produced " + mv.getTotalValue_euro() + "€ of value in sales";
        } else {
            return "";
        }
    }

    /**
     * Gives the descriptions of each row of the corresponding materialized view
     * @return a collection of Strings containing the descriptions of each row
     */
    public List<String> getAllTotalValuePerSpWithOpDescriptions(){
        List<MVTotalValuePerSpWithOp> mvResults = null;
        List<String> descriptions = new ArrayList<>();
        try {
            mvResults = em.createNamedQuery("MVTotalValuePerSpWithOp.getAllTotalValuePerSpWithOp", MVTotalValuePerSpWithOp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalValuePerSpWithOp results");
        }
        if(mvResults != null){
            mvResults.forEach(mv -> descriptions.add(getTotalValuePerSpWithOpDescription(mv)));
        }
        return descriptions;
    }

    /**
     * Gives the String containing the description given the entity mapping the row of the materialized view
     * @param mv entity representing a single row
     * @return a String containing the textual description of the row
     */
    private String getTotalValuePerSpWithOpDescription(MVTotalValuePerSpWithOp mv){
        ServicePackage servicePackage = null;
        try {
            servicePackage = servicePackageService.getServicePackageById(mv.getServicePackageId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve service package");
        }
        if (servicePackage != null) {
            return "Service package " + servicePackage.getName() + " (id: " + servicePackage.getServicePackageId() +
                    ") has produced " + mv.getTotalValue_euro() +
                    "€ of value in sales, including the optional products sold with it";
        } else {
            return "";
        }
    }

    /**
     * Gives the description of the first row of the corresponding materialized view
     * @return a collection of Strings containing the description of the first row
     */
    public Collection<String> getBestSellerOpDescription(){
        List<MVTotalPurchasesPerOp> mvResult = null;
        List<String> descriptions = new ArrayList<>();
        try {
            mvResult = em.createNamedQuery("MVTotalPurchasesPerOp.getBestSellerOp", MVTotalPurchasesPerOp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalPurchasesPerOp results");
        }
        if(mvResult.isEmpty()){
            descriptions.add("");
        } else {
            descriptions.add(getBestSellerOpDescription(mvResult.get(0)));
        }
        return descriptions;

    }

    /**
     * Gives the String containing the description given the entity mapping the row of the materialized view
     * @param mv entity representing a single row
     * @return a String containing the textual description of the row
     */
    private String getBestSellerOpDescription(MVTotalPurchasesPerOp mv){
        OptionalProduct optionalProduct = null;
        try {
            optionalProduct = optionalProductService.getOptionalProductById(mv.getOptionalProductId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve optional product");
        }
        if (optionalProduct != null) {
            return "Optional product " + optionalProduct.getName() + " (id: " + optionalProduct.getOptionalProductId() + ") is the best seller";

        } else {
            return "";
        }
    }
}
