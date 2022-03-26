package it.polimi.db2_project_20212022_fontana_gerosa.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services.TelcoService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.TelcoServiceService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientTelcoService;
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
import java.util.List;

/**
 * Servlet to get all the possible Telco services
 */
@WebServlet("/GetServices")
@MultipartConfig
public class GetServices extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/TelcoServiceService")
    private TelcoServiceService serviceService = new TelcoServiceService();

    public GetServices() { super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the user is not logged in (not present in session) redirect to the login
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("employeeId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User not allowed");
            return;
        }

        List<TelcoService> services;
        List<ClientTelcoService> clientTelcoServices = new ArrayList<>();

        try {
            services = serviceService.getAllServices();
            services.forEach(telcoService -> clientTelcoServices.add(new ClientTelcoService(telcoService)));
        } catch (PersistenceException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Not possible to recover ejbs");
            return;
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(clientTelcoServices);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }


    public void destroy() {
        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
