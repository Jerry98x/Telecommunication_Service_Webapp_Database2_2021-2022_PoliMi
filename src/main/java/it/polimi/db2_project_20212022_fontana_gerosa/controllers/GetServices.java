package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.TelcoServiceService;
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
import java.util.List;

@WebServlet("/GetServices")
@MultipartConfig
public class GetServices extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/TelcoServiceService")
    private TelcoServiceService serviceService = new TelcoServiceService();

    public GetServices() { super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer requestingEmployeeId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("employeeId")));
        if (!requestingEmployeeId.equals(request.getSession().getAttribute("employeeId"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("The requesting employee is not the logged one");
            return;
        }

        List<TelcoService> services;
        List<String> serviceDescriptions = new ArrayList<>();

        try {
            services = serviceService.getAllServices();
            services.forEach(telcoService -> serviceDescriptions.add(telcoService.getDescription()));
        } catch (PersistenceException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Not possible to recover services");
            return;
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(serviceDescriptions);

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