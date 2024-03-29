package it.polimi.db2_project_20212022_fontana_gerosa.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.ServicePackageService;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Servlet to get all the necessary info about a service package given its id
 */
@WebServlet("/GetServicePackageToBuy")
@MultipartConfig
public class GetServicePackageToBuy extends HttpServlet{
    private Connection connection = null;
    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServicePackage servicePackage;
        if (request.getParameter("spIdToBuy") != null) {
            int servicePackageId = Integer.parseInt(request.getParameter("spIdToBuy"));

            try {
                servicePackage = servicePackageService.getServicePackageById(servicePackageId);
            } catch (PersistenceException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Not possible to recover service packages");
                return;
            }

            // Redirect to the Home page and add servicePackages to the parameters
            ClientServicePackage clientServicePackage = new ClientServicePackage(servicePackage);
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(clientServicePackage);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Null spIdToBuy");
        }
    }

    public void destroy() {
        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
