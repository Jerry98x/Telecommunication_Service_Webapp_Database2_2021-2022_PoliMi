package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.*;
import it.polimi.db2_project_20212022_fontana_gerosa.services.*;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientOrder;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ConnectionHandler;
import jakarta.ejb.EJB;
import jakarta.json.Json;
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
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet("/CreateOrder")
@MultipartConfig
public class CreateOrder extends HttpServlet {
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

    public CreateOrder(){ super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        int userId = ;
        Instant confirmationDateInst = ;
        Instant confirmationHourInst = ;
        float totalCost = ;
        Instant startDateInst = ;
        int valid = ;
        int servicePackageId = ;
        int chosenValidityPeriodId = ;
        Collection<Integer> chosenOptionalProductsIds = ;
        Order order = new Order();

        User user = userService.findUserById(userId);
        order.setUser(user);

        LocalDate confirmationDate = LocalDate.ofInstant(confirmationDateInst);

        order.setTotalCost_euro(totalCost);

        order.setValid(valid);

        ServicePackage servicePackage = servicePackageService.getServicePackageById(servicePackageId);
        order.setServicePackage(servicePackage);

        ValidityPeriod chosenValidityPeriod = validityPeriodService.getValidityPeriodById(chosenValidityPeriodId);
        order.setChosenValidityPeriod(chosenValidityPeriod);

        Collection<OptionalProduct> chosenOptionalProducts = new ArrayList<>();
        chosenOptionalProductsIds.forEach(copId -> chosenOptionalProducts.add(optionalProductService.getOptionalProductById(copId)));
        order.setChosenOptionalProducts(chosenOptionalProducts);

        orderService.createNewOrder(order);

         */
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
