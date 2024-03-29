package it.polimi.db2_project_20212022_fontana_gerosa.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.*;
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

/**
 * Servlet to get data to populate the sales report page
 */
@WebServlet("/GetAggregatedData")
@MultipartConfig
public class GetAggregatedData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/MVService")
    private MVService mvService = new MVService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/UserService")
    private UserService userService = new UserService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OrderService")
    private OrderService orderService = new OrderService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/AlertService")
    private AlertService alertService = new AlertService();

    public GetAggregatedData() {
        super();
    }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // If the employee is not logged in (not present in session) redirect to the login
        if (session.isNew() || session.getAttribute("employeeId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User not allowed");
            return;
        }

        Collection<Collection<String>> collectionOfData;
        collectionOfData = new ArrayList<>();

        //total purchases per ServicePackage
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

        //total purchases per pair ServicePackage-ValidityPeriod
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

        //total value per ServicePackage
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

        //total value per ServicePackage including OptionalProducts
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

        //average amount of OptionalProducts per ServicePackage
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

        //best seller OptionalProduct
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

        //insolvent Users
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

        //rejected Orders
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

        //alerts
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
