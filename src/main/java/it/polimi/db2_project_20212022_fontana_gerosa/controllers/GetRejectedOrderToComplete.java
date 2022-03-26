package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.Order;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.OrderService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.ServicePackageService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientOrder;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientServicePackage;
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

/**
 * Servlet to get all the necessary info about a previously rejected order, now to be completed, given its id
 */
@WebServlet("/GetRejectedOrderToComplete")
@MultipartConfig
public class GetRejectedOrderToComplete extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OrderService")
    private OrderService orderService = new OrderService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    public GetRejectedOrderToComplete(){
        super();
    }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the user is not logged in (not present in session) redirect to the login
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User not allowed");
            return;
        }

        // obtain and escape params
        if (request.getParameter("rejectedOrderId") != null) {
            Integer requestedUserId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("rejectedOrderId")));
            Order rejectedOrder = null;
            try {
                rejectedOrder = orderService.getRejectedOrderById(requestedUserId);
            } catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            if (rejectedOrder == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
            } else {
                int loggedUserId = (int) request.getSession().getAttribute("userId");
                if (rejectedOrder.getUser().getUserId() == loggedUserId) {
                    ClientOrder clientRejectedOrder = new ClientOrder(rejectedOrder);
                    ClientServicePackage clientServicePackage = new ClientServicePackage(rejectedOrder.getServicePackage());
                    Gson gson = new GsonBuilder().create();
                    String json;

                    String jsonOrder = gson.toJson(clientRejectedOrder);
                    String jsonPackage = gson.toJson(clientServicePackage);

                    json = "[" + jsonOrder + "," + jsonPackage + "]";

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().println("Requesting user is not the logged one");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Null rejectedOrderId");
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
