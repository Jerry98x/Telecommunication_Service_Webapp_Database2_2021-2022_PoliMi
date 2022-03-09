package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.Collection;
import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public List<Order> getRejectedOrders(int userId){
        List<Order> matchingOrders = null;
        try {
            matchingOrders = em.createNamedQuery("Order.getRejectedOrders", Order.class)
                    .setParameter(1, userId).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve rejected orders");
        }
        return matchingOrders;
    }

    public Order getRejectedOrderById(int orderId) {
        List<Order> matchingOrders = null;
        try {
            matchingOrders = em.createNamedQuery("Order.getRejectedOrderById", Order.class)
                    .setParameter(1, orderId).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve rejected orders");
        }
        if (matchingOrders.isEmpty()) {
            return null;
        }
        return matchingOrders.get(0);
    }

    public void createNewOrder (Order newOrder){
        em.persist(newOrder);
        em.flush();
    }
}
