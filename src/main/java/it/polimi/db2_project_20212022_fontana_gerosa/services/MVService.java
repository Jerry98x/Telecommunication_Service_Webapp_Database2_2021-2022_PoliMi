package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.mv.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class MVService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public List<MVAvgAmountOpPerSp> getAllAvgAmountOpPerSp(){
        List<MVAvgAmountOpPerSp> mvResults = null;
        try {
            mvResults = em.createNamedQuery("MVAvgAmountOpPerSp.getAllAvgAmountOpPerSp", MVAvgAmountOpPerSp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve mv results");
        }
        return mvResults;
    }

    public List<MVTotalPurchasesPerSp> getAllTotalPurchasesPerSp(){
        List<MVTotalPurchasesPerSp> mvResults = null;
        try {
            mvResults = em.createNamedQuery("MVTotalPurchasesPerSp.getAllTotalPurchasesPerSp", MVTotalPurchasesPerSp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve mv results");
        }
        return mvResults;
    }

    public List<MVTotalPurchasesPerSpAndVp> getAllTotalPurchasesPerSpAndVp(){
        List<MVTotalPurchasesPerSpAndVp> mvResults = null;
        try {
            mvResults = em.createNamedQuery("MVTotalPurchasesPerSpAndVp.getAllTotalPurchasesPerSpAndVp", MVTotalPurchasesPerSpAndVp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve mv results");
        }
        return mvResults;
    }

    public List<MVTotalValuePerSp> getAllTotalValuePerSp(){
        List<MVTotalValuePerSp> mvResults = null;
        try {
            mvResults = em.createNamedQuery("MVTotalValuePerSp.getAllTotalValuePerSp", MVTotalValuePerSp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve mv results");
        }
        return mvResults;
    }

    public List<MVTotalValuePerSpWithOp> getAllTotalValuePerSpWithOp(){
        List<MVTotalValuePerSpWithOp> mvResults = null;
        try {
            mvResults = em.createNamedQuery("MVTotalValuePerSpWithOp.getAllTotalValuePerSpWithOp", MVTotalValuePerSpWithOp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve mv results");
        }
        return mvResults;
    }

    public List<MVTotalPurchasesPerOp> getAllTotalPurchasesPerOp(){
        List<MVTotalPurchasesPerOp> mvResults = null;
        try {
            mvResults = em.createNamedQuery("MVTotalPurchasesPerOp.getAllTotalPurchasesPerOp", MVTotalPurchasesPerOp.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve mv results");
        }
        return mvResults;
    }
}
