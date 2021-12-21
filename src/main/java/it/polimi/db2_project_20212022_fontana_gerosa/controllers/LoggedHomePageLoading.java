package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.HomePageContent;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.services.OrderService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LoggedHomePageLoading")
public class LoggedHomePageLoading extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        OrderService orderService = new OrderService();
        List<Order> pendingOrders = new ArrayList<>();
        List<Order> rejectedOrders = new ArrayList<>();
        ServicePackageService servicePackageService = new ServicePackageService();
        List<ServicePackage> servicePackages = new ArrayList<>();

        try {
            pendingOrders = orderService.getPendingOrders(user);
        } catch (PersistenceException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Not possible to recover pending orders");
            return;
        }

        if(user.isInsolvent()) {
            try {
                rejectedOrders = orderService.getRejectedOrders(user);
            } catch (PersistenceException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Not possible to recover rejected orders");
                return;
            }
        }

        try {
            servicePackages = servicePackageService.getAllServicePackages();
        } catch (PersistenceException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Not possible to recover service packages");
            return;
        }

        // Redirect to the Home page and add servicePackages to the parameters

        Gson gson = new GsonBuilder().create();

        HomePageContent hpc = new HomePageContent(user, pendingOrders, rejectedOrders, servicePackages);
        String json = gson.toJson(hpc);


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
