package it.polimi.db2_project_20212022_fontana_gerosa.servlets;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.OrderService;
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
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Servlet to get all rejected orders of a user given their id
 */
@WebServlet("/GetRejectedOrders")
@MultipartConfig
public class GetRejectedOrders extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;


    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OrderService")
    private OrderService orderService = new OrderService();

    public GetRejectedOrders(){super();}

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the user is not logged in (not present in session) redirect to the login
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("User not allowed");
            return;
        }

        // obtain and escape params
        if (request.getParameter("userId") != null) {
            Integer userId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("userId")));
            if (!userId.equals(request.getSession().getAttribute("userId"))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("The requesting user is not the logged one.");
                return;
            }

            Collection<Order> rejectedOrders = null;
            try {
                rejectedOrders = orderService.getRejectedOrdersByUserId(userId);
            } catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }

            Gson gson = new GsonBuilder().create();
            String json;

            if (rejectedOrders != null) {
                Collection<ClientOrder> clientRejectedOrders = new ArrayList<>();
                rejectedOrders.forEach(order -> clientRejectedOrders.add(new ClientOrder(order)));
                json = gson.toJson(clientRejectedOrders);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Null userId");
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
