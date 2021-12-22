package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "TelcoApp")
    private EntityManager em;

    public List<ServicePackage> getAllServicePackages(){
        return em.createNamedQuery("ServicePackage.getAllServicePackages", ServicePackage.class).getResultList();
    }
}
