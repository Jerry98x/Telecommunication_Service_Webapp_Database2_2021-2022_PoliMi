package it.polimi.db2_project_20212022_fontana_gerosa.servlets;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.ValidityPeriod;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.telco_services.TelcoService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.OptionalProductService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.ServicePackageService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.TelcoServiceService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.ValidityPeriodService;
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
import java.util.StringTokenizer;

/**
 * Servlet to manage the process of creation of a new service package by an employee
 */
@WebServlet("/CreateServicePackage")
@MultipartConfig
public class CreateServicePackage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ServicePackageService")
    private ServicePackageService servicePackageService = new ServicePackageService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/TelcoServiceService")
    private TelcoServiceService serviceService = new TelcoServiceService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OptionalProductService")
    private OptionalProductService optionalProductService = new OptionalProductService();

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService = new ValidityPeriodService();


    public CreateServicePackage() { super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the employee is not logged in (not present in session) redirect to the login
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("employeeId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User not allowed");
            return;
        }

        //escaping params
        String servicePackageName = StringEscapeUtils.escapeJava(request.getParameter("sp_name"));

        String servicePackageServicesString = StringEscapeUtils.escapeJava(request.getParameter("inputServices"));
        StringTokenizer stringTokenizerServices = new StringTokenizer(servicePackageServicesString, "$");
        Collection<Integer> servicePackageServicesId = new ArrayList<>();
        while (stringTokenizerServices.hasMoreTokens()) {
            servicePackageServicesId.add(Integer.parseInt(stringTokenizerServices.nextToken()));
        }

        String servicePackageOptionalProductsString = StringEscapeUtils.escapeJava(request.getParameter("inputOptionalProducts"));
        StringTokenizer stringTokenizerOptionalProducts = new StringTokenizer(servicePackageOptionalProductsString, "$");
        Collection<Integer> servicePackageOptionalProductsId = new ArrayList<>();
        while (stringTokenizerOptionalProducts.hasMoreTokens()) {
            servicePackageOptionalProductsId.add(Integer.parseInt(stringTokenizerOptionalProducts.nextToken()));
        }

        String servicePackageValidityPeriodsString = StringEscapeUtils.escapeJava(request.getParameter("inputValidityPeriods"));
        StringTokenizer stringTokenizerValidityPeriods = new StringTokenizer(servicePackageValidityPeriodsString, "$");
        Collection<Integer> servicePackageValidityPeriodsId = new ArrayList<>();
        while (stringTokenizerValidityPeriods.hasMoreTokens()) {
            servicePackageValidityPeriodsId.add(Integer.parseInt(stringTokenizerValidityPeriods.nextToken()));
        }

        ServicePackage servicePackage = new ServicePackage();

        Collection<TelcoService> actualServices = new ArrayList<>();
        Collection<OptionalProduct> actualOptionalProducts = new ArrayList<>();
        Collection<ValidityPeriod> actualValidityPeriods = new ArrayList<>();

        //if params are correct entity is built
        if(servicePackageName != null && !servicePackageName.equals("") && !servicePackageServicesId.isEmpty() && !servicePackageValidityPeriodsId.isEmpty()) {
            try {
                servicePackage.setName(servicePackageName);
            }
            catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }

            try {
                servicePackageServicesId.forEach(s_id -> actualServices.add(serviceService.getServiceById(s_id)));
            }
            catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }

            try {
                servicePackageOptionalProductsId.forEach(op_id -> actualOptionalProducts.add(optionalProductService.getOptionalProductById(op_id)));
            }
            catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }

            try {
                servicePackageValidityPeriodsId.forEach(vp_id -> actualValidityPeriods.add(validityPeriodService.getValidityPeriodById(vp_id)));
            }
            catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println("Name field must not be empty. Choose at least one service and at least one validity period.");
            return;
        }


        //service package actual construction
        servicePackage.setServices(actualServices);
        servicePackage.setAvailableOptionalProducts(actualOptionalProducts);
        servicePackage.setAvailableValidityPeriods(actualValidityPeriods);

        try {
            servicePackageService.insertServicePackage(servicePackage);
        }
        catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }


        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Service package " + "\"" + servicePackage.getName() + "\"" + " has been correctly created.");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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
