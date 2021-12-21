package it.polimi.db2_project_20212022_fontana_gerosa;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.HomePageServicePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomePageContent implements Serializable {
    private String username;
    private boolean insolvent;
    private List<Integer> pendingOrdersIds;
    private List<Integer> rejectedOrdersIds;
    private List<HomePageServicePackage> homePageServicePackages;

    public HomePageContent(User user, List<ServicePackage> servicePackages){
        this.username = user.getUsername();
        this.insolvent = user.isInsolvent();
        this.pendingOrdersIds = null;
        this.rejectedOrdersIds = null;
        this.homePageServicePackages = new ArrayList<>();
        servicePackages.forEach(servicePackage -> this.homePageServicePackages.add(new HomePageServicePackage(servicePackage)));
    }

    public HomePageContent(User user, List<Order> pendingOrders, List<Order> rejectedOrders, List<ServicePackage> servicePackages){
        this.username = user.getUsername();
        this.insolvent = user.isInsolvent();
        this.pendingOrdersIds = new ArrayList<>();
        pendingOrders.forEach(pendingOrder -> this.pendingOrdersIds.add(pendingOrder.getOrderId()));
        this.rejectedOrdersIds = new ArrayList<>();
        rejectedOrders.forEach(rejectedOrder -> this.rejectedOrdersIds.add(rejectedOrder.getOrderId()));
        this.homePageServicePackages = new ArrayList<>();
        servicePackages.forEach(servicePackage -> this.homePageServicePackages.add(new HomePageServicePackage(servicePackage)));
    }
}
