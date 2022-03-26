package it.polimi.db2_project_20212022_fontana_gerosa.ejbs;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * EJB to manage order entities
 */
@Stateless
public class OrderService {
    @PersistenceContext(unitName = "DB2_Project_2021-2022_Fontana_Gerosa")
    private EntityManager em;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/UserService")
    private UserService userService = new UserService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    /**
     * Gives all rejected Orders made by a User given their id
     * @param userId id of the User who the orders belong to
     * @return a collection of rejected Orders belonging to the User
     */
    public List<Order> getRejectedOrdersByUserId(int userId) {
        List<Order> matchingOrders = null;
        try {
            matchingOrders = em.createNamedQuery("Order.getRejectedOrders", Order.class)
                    .setParameter(1, userId).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve rejected orders");
        }
        return matchingOrders;
    }

    /**
     * Gives a rejected Order given its id
     * @param orderId id of the Order to be searched
     * @return the found rejected Order
     */
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

    /**
     * Gives a collection of all the Orders of a User given their id
     * @param userId id of the User who the Orders belong to
     * @return a collection of Orders belonging to the User
     */
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

    /**
     * Creates a new Order in the DB and (eventually) resolves the insolvency attribute of the User who made it
     * @param newOrder the Order to be added in the DB
     */
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
                    user.setNumOfFailedPayments(user.getNumOfFailedPayments()%3);
                }
            }
            try {
                em.merge(user);
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't update user");
            }

            try {
                em.persist(newOrder);
                em.flush();
            } catch (PersistenceException e){
                throw new PersistenceException("Couldn't add new order");
            }
        }
        else {
            throw new PersistenceException("Couldn't find the related user");
        }

    }

    /**
     * Updates an old Order in the DB and (eventually) resolves the insolvency attribute of the User who made it
     * @param order the Order to be updated in the DB
     */
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
                    user.setNumOfFailedPayments(user.getNumOfFailedPayments()%3);
                }
            }
            try {
                em.merge(user);
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't update user");
            }
            try {
                em.merge(order);
                em.flush();
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't update order");
            }
        }
        else {
            throw new PersistenceException("Couldn't find the related user");
        }
    }

    /**
     * Makes the User who made the Order insolvent and resolves the creation of an (eventual) alert
     * @param user the User who made the Order and becomes insolvent
     * @param order the (rejected) Order which made the User insolvent
     */
    private void makeUserInsolvent(User user, Order order){
        user.setInsolvent(1);
        user.setNumOfFailedPayments(user.getNumOfFailedPayments() + 1);
        checkAlert(user, order);
    }

    /**
     * Checks if an Alert needs to be raised and eventually raises it
     * @param user the User to be checked to raise the Alert
     * @param order the Order to raise the Alert on
     */
    private void checkAlert(User user, Order order){
        if (user.getNumOfFailedPayments()%3 == 0) {//over 3 failed payments
            Alert alert = new Alert(user, order);
            try {
                em.persist(alert);
            } catch (PersistenceException e) {
                throw new PersistenceException("Couldn't create alert");
            }
        }
    }

    /**
     * Gives a collection of Strings containing the descriptions of each rejected Orders in the DB
     * @return a collection of Strings containing the textual descriptions
     */
    public Collection<String> getAllRejectedOrdersDescriptions(){
        Collection<Order> orders = null;
        Collection<String> descriptions = new ArrayList<>();
        try {
            orders = em.createNamedQuery("Order.getAllRejectedOrders", Order.class).getResultList();
        } catch (PersistenceException e){
            throw new PersistenceException("Couldn't retrieve MVTotalPurchasesPerOp results");
        }
        if(orders != null){
            orders.forEach(order -> descriptions.add(getRejectedOrderDescription(order)));
        }
        return descriptions;
    }

    /**
     * Gives the textual description of a given rejected Order
     * @param order the rejected Order to be described
     * @return a String containing the description
     */
    private String getRejectedOrderDescription(Order order){
        User user = null;
        ServicePackage servicePackage = null;
        try {
            user = userService.getUserByOrderId(order.getOrderId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve user");
        }
        try {
            servicePackage = servicePackageService.getServicePackageByOrderId(order.getOrderId());
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve service package");
        }

        if (user != null && servicePackage != null) {
            return "Order n." + order.getOrderId() + " of " + order.getTotalCost_euro() + " for service package " + servicePackage.getName() +
                    " tried by user " + user.getUsername() + "(id: " + user.getUserId() + ") and rejected on " + order.getCreationDate().toString() +
                    " at " + order.getCreationHour().toString();
        } else {
            return "";
        }
    }
}
