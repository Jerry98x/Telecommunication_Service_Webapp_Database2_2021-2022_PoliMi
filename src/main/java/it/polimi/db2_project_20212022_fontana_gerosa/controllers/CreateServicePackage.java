package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.services.OptionalProductService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.TelcoServiceService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ValidityPeriodService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ConnectionHandler;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/CreateServicePackage")
@MultipartConfig
public class CreateServicePackage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/TelcoServiceService")
    private TelcoServiceService serviceService = new TelcoServiceService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/OptionalProductService")
    private OptionalProductService optionalProductService = new OptionalProductService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService = new ValidityPeriodService();


    public CreateServicePackage() { super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServicePackage servicePackage = null;


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
