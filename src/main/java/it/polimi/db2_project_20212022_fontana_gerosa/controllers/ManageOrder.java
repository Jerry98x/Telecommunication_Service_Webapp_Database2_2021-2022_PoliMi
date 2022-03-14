package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.*;
import it.polimi.db2_project_20212022_fontana_gerosa.services.*;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientOrder;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ConnectionHandler;
import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

@WebServlet("/ManageOrder")
@MultipartConfig
public class ManageOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/OrderService")
    private OrderService orderService = new OrderService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/UserService")
    private UserService userService = new UserService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService = new ValidityPeriodService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/OptionalProductService")
    private OptionalProductService optionalProductService = new OptionalProductService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    public ManageOrder(){ super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = null;
        int orderId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("orderId")));
        String startDateString = StringEscapeUtils.escapeJava(request.getParameter("startDate"));
        int valid = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("valid")));
        if(orderId == -1) {
            order = new Order();
            //parse request
            float totalCost = Float.parseFloat(StringEscapeUtils.escapeJava(request.getParameter("totalCost")));
            int userId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("userId")));
            int servicePackageId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("servicePackageId")));
            String chosenOptionalProductsIdsString = StringEscapeUtils.escapeJava(request.getParameter("chosenOptionalProductsIds"));
            StringTokenizer stringTokenizer = new StringTokenizer(chosenOptionalProductsIdsString, "$");
            Collection<Integer> chosenOptionalProductsIds = new ArrayList<>();
            while (stringTokenizer.hasMoreTokens()) {
                chosenOptionalProductsIds.add(Integer.parseInt(stringTokenizer.nextToken()));
            }
            int chosenValidityPeriodId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("chosenValidityPeriodId")));
            //set attributes
            float tot = 0;

            //user
            User user = null;
            try {
                user = userService.findUserById(userId);
            } catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            if(user != null) {
                order.setUser(user);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("");
                return;
            }
            //service package
            ServicePackage servicePackage = null;
            try {
                servicePackage = servicePackageService.getServicePackageById(servicePackageId);
            } catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            if(servicePackage != null) {
                order.setServicePackage(servicePackage);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("The requested service package doesn't exist");
                return;
            }
            //validity period
            ValidityPeriod chosenValidityPeriod = null;
            try {
                chosenValidityPeriod = validityPeriodService.getValidityPeriodById(chosenValidityPeriodId);
            } catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            ValidityPeriod finalChosenValidityPeriod = chosenValidityPeriod;
            if(chosenValidityPeriod != null && servicePackage.getAvailableValidityPeriods().stream().
                    anyMatch(validityPeriod -> validityPeriod.getValidityPeriodId() == finalChosenValidityPeriod.getValidityPeriodId())) {
                order.setChosenValidityPeriod(chosenValidityPeriod);
                tot += chosenValidityPeriod.getMonthlyFee_euro();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("The requested validity period doesn't exist among the available ones");
                return;
            }
            //optional products
            if(!chosenOptionalProductsIds.isEmpty()) {
                Collection<OptionalProduct> chosenOptionalProducts = new ArrayList<>();
                try {
                    chosenOptionalProductsIds.forEach(copId -> chosenOptionalProducts.add(optionalProductService.getOptionalProductById(copId)));
                } catch (PersistenceException e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("Internal server error, retry later");
                    return;
                }
                ServicePackage finalServicePackage = servicePackage;
                if (!chosenOptionalProducts.isEmpty() &&
                        chosenOptionalProducts.stream().allMatch(chosenOptionalProduct -> finalServicePackage.getAvailableOptionalProducts().stream().
                                anyMatch(optionalProduct -> optionalProduct.getOptionalProductId() == chosenOptionalProduct.getOptionalProductId()))){
                    order.setChosenOptionalProducts(chosenOptionalProducts);
                    tot += chosenOptionalProducts.stream().mapToDouble(OptionalProduct::getMonthlyFee_euro).sum();
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("One of the requested products doesn't exist among the available ones");
                    return;
                }
            }
            //total cost
            if(tot == totalCost) {
                order.setTotalCost_euro(totalCost);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Total cost is not right");
                return;
            }
            //creation date
            order.setCreationDate(LocalDate.now());
            //creation time
            order.setCreationHour(LocalTime.now());
        } else {
            try {
                order = orderService.getRejectedOrderById(orderId);
            } catch (PersistenceException e){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            if(order == null){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("The requested order doesn't exists or it is already completed");
                return;
            }
        }
        LocalDate startDate = LocalDate.parse(startDateString);
        if(startDate.isBefore(LocalDate.now())){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("The start date is passed");
            return;
        } else {
            order.setStartDate(startDate);
        }
        if(valid == 0 || valid == 1){
            order.setValid(valid);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("The valid parameter is wrong");
            return;
        }
        if(orderId == -1) {//TODO maybe one method is sufficient
            orderService.createNewOrder(order);
        } else {
            orderService.updateOrder(order);
        }


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
