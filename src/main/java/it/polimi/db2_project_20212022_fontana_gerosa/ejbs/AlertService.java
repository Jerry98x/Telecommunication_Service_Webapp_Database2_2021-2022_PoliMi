package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.Alert;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;

@Stateless
public class AlertService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public Collection<String> getAllAlertsDescriptions(){
        Collection<Alert> alerts = null;
        Collection<String> descriptions = new ArrayList<>();
        try {
            alerts = em.createNamedQuery("Alert.getAllAlerts", Alert.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalPurchasesPerOp results");
        }
        if(alerts != null){
            alerts.forEach(alert -> descriptions.add(alert.getDescription()));
        }
        return descriptions;
    }
}
