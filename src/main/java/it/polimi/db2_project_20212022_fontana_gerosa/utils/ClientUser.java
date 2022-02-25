package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;

import java.util.Collection;

public class ClientUser {
    private int userId;
    private String username;
    private int insolvent;
    private Collection<Order> rejectedOrders;

    public ClientUser(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.insolvent = user.isInsolvent();
    }



}
