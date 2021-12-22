package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "TelcoApp")
    private EntityManager em;

    public List<Order> getRejectedOrders(User user){
        return em.createNamedQuery("Order.getRejectedOrders", Order.class)
                .setParameter(1, user.getUserId()).getResultList();
    }

    public List<Order> getPendingOrders(User user){
        return em.createNamedQuery("Order.getPendingOrders", Order.class)
                .setParameter(1, user.getUserId()).getResultList();
    }
}
