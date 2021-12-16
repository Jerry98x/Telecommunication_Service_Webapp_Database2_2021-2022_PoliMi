package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

public class ServicePackageService {
    private EntityManager em;

    public List<ServicePackage> getAllServicePackages(){
        return em.createNamedQuery("ServicePackage.getAllServicePackages", ServicePackage.class).getResultList();
    }
}
