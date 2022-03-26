//package it.polimi.db2_project_20212022_fontana_gerosa.servlets;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import it.polimi.db2_project_20212022_fontana_gerosa.entities.mvs.*;
//import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.AlertService;
//import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.MVService;
//import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.OrderService;
//import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.UserService;
//import it.polimi.db2_project_20212022_fontana_gerosa.utils.*;
//import jakarta.ejb.EJB;
//import jakarta.persistence.PersistenceException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.List;
//
//@WebServlet("/GetAggregatedData")
//@MultipartConfig
//public class GetAggregatedData extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private Connection connection = null;
//
//    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/MVService")
//    private MVService mvService = new MVService();
//    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/UserService")
//    private UserService userService = new UserService();
//    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OrderService")
//    private OrderService orderService = new OrderService();
//    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/AlertService")
//    private AlertService alertService = new AlertService();
//
//    public GetAggregatedData() { super(); }
//
//    public void init() throws ServletException {
//        connection = ConnectionHandler.getConnection(getServletContext());
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // If the user is not logged in (not present in session) redirect to the login
//        HttpSession session = request.getSession();
//        if (session.isNew() || session.getAttribute("employeeId") == null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().println("User not allowed");
//            return;
//        }
//
//        List<MVTotalPurchasesPerSp> mvTotalPurchasesPerSps;
//        try {
//            mvTotalPurchasesPerSps = mvService.getAllTotalPurchasesPerSp();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<MVTotalPurchasesPerSpAndVp> mvTotalPurchasesPerSpAndVps;
//        try {
//            mvTotalPurchasesPerSpAndVps = mvService.getAllTotalPurchasesPerSpAndVp();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<MVTotalValuePerSp> mvTotalValuePerSps;
//        try {
//            mvTotalValuePerSps = mvService.getAllTotalValuePerSp();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<MVTotalValuePerSpWithOp> mvTotalValuePerSpWithOps;
//        try {
//            mvTotalValuePerSpWithOps = mvService.getAllTotalValuePerSpWithOp();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<MVAvgAmountOpPerSp> mvAvgAmountOpPerSps;
//        try {
//            mvAvgAmountOpPerSps = mvService.getAllAvgAmountOpPerSp();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<MVTotalPurchasesPerOp> mvTotalPurchasesPerOps;
//        try {
//            mvTotalPurchasesPerOps = mvService.getAllTotalPurchasesPerOp();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//
//        List<ClientUser> clientInsolventUsers;
//        try {
//            clientInsolventUsers = userService.findClientInsolventUsers();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<ThinnerClientOrder> allThinnerClientRejectedOrders;
//        try {
//            allThinnerClientRejectedOrders = orderService.getAllThinnerClientRejectedOrders();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//        List<ClientAlert> clientAlerts;
//        try {
//            clientAlerts = alertService.getAllClientAlerts();
//        }
//        catch (PersistenceException e){
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().println("Not possible to recover optional products");
//            return;
//        }
//
//
//        Gson gson = new GsonBuilder().create();
//
//        String json1 = gson.toJson(mvTotalPurchasesPerSps);
//        String json2 = gson.toJson(mvTotalPurchasesPerSpAndVps);
//        String json3 = gson.toJson(mvTotalValuePerSps);
//        String json4 = gson.toJson(mvTotalValuePerSpWithOps);
//        String json5 = gson.toJson(mvAvgAmountOpPerSps);
//        String json6 = gson.toJson(mvTotalPurchasesPerOps);
//
//        String jsonSalesData;
//        jsonSalesData = "["+json1+","+json2+","+json3+","+json4+","+json5+","+json6+"]";
//
//        String json7 = gson.toJson(clientInsolventUsers);
//        String json8 = gson.toJson(allThinnerClientRejectedOrders);
//        String json9 = gson.toJson(clientAlerts);
//
//        String jsonUOA;
//        jsonUOA = "["+json7+","+json8+","+json9+"]";
//
//        String json = "["+jsonSalesData+","+jsonUOA+"]";
//
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(json);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
//    }
//
//    public void destroy() {
//        try {
//            ConnectionHandler.closeConnection(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
