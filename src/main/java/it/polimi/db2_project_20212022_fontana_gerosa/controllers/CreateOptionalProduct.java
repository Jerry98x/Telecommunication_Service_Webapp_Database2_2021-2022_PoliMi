package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import it.polimi.db2_project_20212022_fontana_gerosa.entities.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.OptionalProductService;
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

@WebServlet("/CreateOptionalProduct")
@MultipartConfig
public class CreateOptionalProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/OptionalProductService")
    private OptionalProductService optionalProductService = new OptionalProductService();

    public CreateOptionalProduct() { super(); }

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

        String optionalProductName = StringEscapeUtils.escapeJava(request.getParameter("op_name"));
        float optionalProductFee = Float.parseFloat(request.getParameter("op_fee"));

        OptionalProduct optionalProduct = new OptionalProduct();

        if(optionalProductName != null && !optionalProductName.equals("") && optionalProductFee != 0) {
            try {
                optionalProduct.setName(optionalProductName);
            }
            catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            try {
                optionalProduct.setMonthlyFee_euro(optionalProductFee);
            }
            catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println("Fields must not be empty.");
            return;
        }

        //optional product actual construction
        optionalProductService.insertOptionalProduct(optionalProduct);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Optional product " + "\"" + optionalProduct.getName() + "\"" + " has been correctly created.");
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
