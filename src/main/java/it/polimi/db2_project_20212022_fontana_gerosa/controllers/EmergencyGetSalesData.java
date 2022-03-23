package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.services.*;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ConnectionHandler;
import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/EmergencyGetSalesData")
@MultipartConfig
public class EmergencyGetSalesData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/MVService")
    private MVService mvService = new MVService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/UserService")
    private UserService userService = new UserService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/OrderService")
    private OrderService orderService = new OrderService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/AlertService")
    private AlertService alertService = new AlertService();

    public EmergencyGetSalesData() {
        super();
    }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("employeeId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User not allowed");
            return;
        }

        Collection<Collection<String>> collectionOfData;
        collectionOfData = new ArrayList<>();

        Collection<String> mvTotalPurchasesPerSpStrings = null;
        try{
            mvTotalPurchasesPerSpStrings = mvService.getAllTotalPurchasesPerSpDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(mvTotalPurchasesPerSpStrings != null){
            collectionOfData.add(mvTotalPurchasesPerSpStrings);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> mvTotalPurchasesPerSpAndVpStrings = null;
        try{
            mvTotalPurchasesPerSpAndVpStrings = mvService.getAllTotalPurchasesPerSpAndVpDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(mvTotalPurchasesPerSpAndVpStrings != null){
            collectionOfData.add(mvTotalPurchasesPerSpAndVpStrings);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> mvTotalValuePerSpStrings = null;
        try{
            mvTotalValuePerSpStrings = mvService.getAllTotalValuePerSpDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(mvTotalValuePerSpStrings != null){
            collectionOfData.add(mvTotalValuePerSpStrings);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> mvTotalValuePerSpWithOpStrings = null;
        try{
            mvTotalValuePerSpWithOpStrings = mvService.getAllTotalValuePerSpWithOpDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(mvTotalValuePerSpWithOpStrings != null){
            collectionOfData.add(mvTotalValuePerSpWithOpStrings);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> mvAvgAmountOpPerSpStrings = null;
        try{
            mvAvgAmountOpPerSpStrings = mvService.getAllAvgAmountOpPerSpDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(mvAvgAmountOpPerSpStrings != null){
            collectionOfData.add(mvAvgAmountOpPerSpStrings);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> mvBestSellerOpStrings = null;
        try{
            mvBestSellerOpStrings = mvService.getBestSellerOpDescription();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(mvBestSellerOpStrings != null){
            collectionOfData.add(mvBestSellerOpStrings);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> insolventUsers = null;
        try{
            insolventUsers = userService.getAllInsolventUsersDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(insolventUsers != null){
            collectionOfData.add(insolventUsers);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> rejectedOrders = null;
        try{
            rejectedOrders = orderService.getAllRejectedOrdersDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(rejectedOrders != null){
            collectionOfData.add(rejectedOrders);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Collection<String> alerts = null;
        try{
            alerts = alertService.getAllAlertsDescriptions();
        } catch (PersistenceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if(alerts != null){
            collectionOfData.add(alerts);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(collectionOfData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

    public void destroy() {
        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
