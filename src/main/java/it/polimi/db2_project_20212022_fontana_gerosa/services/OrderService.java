package it.polimi.db2_project_20212022_fontana_gerosa.services;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Alert;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import jakarta.ejb.EJB;
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

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/UserService")
    private UserService userService = new UserService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/AlertService")
    private AlertService alertService = new AlertService();

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

    public Collection<Order> getOrdersByUserId(int userId){
        List<Order> matchingOrders = null;
        try {
            matchingOrders = em.createNamedQuery("Order.getOrdersByUserId", Order.class)
                    .setParameter(1, userId).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve orders");
        }
        return matchingOrders;
    }

    public void createNewOrder (Order newOrder){
        User user = null;
        try {
            user = userService.findUserById(newOrder.getUser().getUserId());
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't find the related user");
        }
        if(user != null){
            if(newOrder.getValid() == 0) {//new failed payment
                makeUserInsolvent(user, newOrder);
            } else if (user.getInsolvent() == 1) {
                Collection<Order> ordersOfUser;
                ordersOfUser = this.getOrdersByUserId(user.getUserId());
                if (ordersOfUser.stream().noneMatch(orderOfUser -> orderOfUser.getValid() == 0)) {//no more insolvent
                    user.setInsolvent(0);
                }
            }
            try {
                em.merge(user);
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't update user");
            }
        }
        try {
            em.persist(newOrder);
            em.flush();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't add new order");
        }
    }

    public void updateOrder(Order order) {
        User user = null;
        try {
            user = userService.findUserById(order.getUser().getUserId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't find the related user");
        }
        if (user != null) {
            if (order.getValid() == 0) {//failed again
                makeUserInsolvent(user, order);
            } else if (user.getInsolvent() == 1) {
                Collection<Order> ordersOfUser;
                ordersOfUser = this.getOrdersByUserId(user.getUserId());
                if (ordersOfUser.stream().noneMatch(orderOfUser -> orderOfUser.getValid() == 0 && orderOfUser.getOrderId() != order.getOrderId())) {//no more insolvent
                    user.setInsolvent(0);
                }
            }
            try {
                em.merge(user);
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't update user");
            }
        }
        try {
            em.merge(order);
            em.flush();
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't update order");
        }
    }

    private void makeUserInsolvent(User user, Order order){
        user.setInsolvent(1);
        user.setNumOfFailedPayments(user.getNumOfFailedPayments() + 1);
        checkAlert(user, order);
    }

    private void checkAlert(User user, Order order){
        if (user.getNumOfFailedPayments() >= 3) {//over 3 failed payments
            Alert alert = new Alert(user, order);
            try {
                em.persist(alert);
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't create alert");
            }
        }
    }
}
