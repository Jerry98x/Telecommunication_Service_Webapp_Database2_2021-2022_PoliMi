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

    public List<Order> getRejectedOrders(User user){
        return em.createNamedQuery("Order.getRejectedOrders", Order.class)
                .setParameter(1, user.getUserId()).getResultList();
    }

//    public Order createnewOrder(ServicePackage sp) {
//        Order newOrder = new Order();
//        newOrder.setServicePackage(sp.getServicePackageId());
//    }
}
