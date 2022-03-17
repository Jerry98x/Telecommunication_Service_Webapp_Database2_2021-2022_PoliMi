package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ValidityPeriod;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.OptionalProductService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.TelcoServiceService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ValidityPeriodService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

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

        if(servicePackageName!= null && !servicePackageServicesId.isEmpty() && !servicePackageValidityPeriodsId.isEmpty()) {
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

        servicePackageService.insertServicePackage(servicePackage);

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