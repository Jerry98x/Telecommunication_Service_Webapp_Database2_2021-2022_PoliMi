package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    public List<Order> getRejectedOrders(int userId){
        return em.createNamedQuery("Order.getRejectedOrders", Order.class)
                .setParameter(1, userId).getResultList();
    }

    public Order getRejectedOrderById(int orderId){
        return em.createNamedQuery("Order.getRejectedOrderById", Order.class)
                .setParameter(1, orderId).getResultList().get(0);
    }

//    public Order createnewOrder(ServicePackage sp) {
//        Order newOrder = new Order();
//        newOrder.setServicePackage(sp.getServicePackageId());
//    }
}
