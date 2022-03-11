package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.Employee;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.services.OrderService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientEmployee;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientOrder;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientUser;
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

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/GetRejectedOrders")
@MultipartConfig
public class GetRejectedOrders extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/OrderService")
    private OrderService orderService = new OrderService();

    public GetRejectedOrders(){
        super();
    }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // obtain and escape params
        Integer requestedUserId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("userId")));
        if (!requestedUserId.equals(request.getSession().getAttribute("userId"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("The requesting user is not the logged one");
            return;
        }
        Collection<Order> rejectedOrders = null;
        try {
            rejectedOrders = orderService.getRejectedOrders(requestedUserId);
        } catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        if (rejectedOrders == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
        } else {
            Collection<ClientOrder> clientRejectedOrders = new ArrayList<>();
            rejectedOrders.forEach(order -> clientRejectedOrders.add(new ClientOrder(order)));
            Gson gson = new GsonBuilder().create();
            String json;

            json = gson.toJson(clientRejectedOrders);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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