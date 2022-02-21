package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.services.OrderService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;

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
        List<Order> rejectedOrders = new ArrayList<>();

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

        // Redirect to the Home page and add servicePackages to the parameters

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(null);


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}

