package it.polimi.db2_project_20212022_fontana_gerosa.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.Employee;
import it.polimi.db2_project_20212022_fontana_gerosa.entities.User;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.EmployeeService;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.UserService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientEmployee;
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

/**
 * Servlet to manage the login procedure of both users and employees
 */
@WebServlet("/CheckLogin")
@MultipartConfig
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/UserService")
    private UserService userService = new UserService();
    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/EmployeeService")
    private EmployeeService employeeService = new EmployeeService();

    public CheckLogin() {
        super();
    }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // obtain and escape params
        String email = null;
        String password = null;
        email = StringEscapeUtils.escapeJava(request.getParameter("login_email"));
        password = StringEscapeUtils.escapeJava(request.getParameter("login_password"));
        if (email == null || password == null || email.isEmpty() || password.isEmpty() ) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Credentials must be not null");
            return;
        }
        // query db to authenticate (check user)
        User user = null;
        try {
            user = userService.checkCredentials(email, password);
            if(user != null) {
                request.getSession().setAttribute("userId", user.getUserId());
            }

        }
        catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        catch (CredentialException e) {
            e.printStackTrace();
        }

        // query db to authenticate (check employee)
        Employee employee = null;
        try {
            employee = employeeService.checkEmployeeCredentials(email, password);
            if(employee != null) {
                request.getSession().setAttribute("employeeId", employee.getEmployeeId());
            }

        }
        catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        catch (CredentialException e) {
            e.printStackTrace();
        }


        if (user == null && employee == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Incorrect credentials");
        }
        else {
            Gson gson = new GsonBuilder().create();
            String json;

            if(user != null) {
                ClientUser clientUser = new ClientUser(user);
                String json1 = gson.toJson(clientUser);
                String json2 = gson.toJson(false);
                json = "["+json1+","+json2+"]";
            }
            else {
                ClientEmployee clientEmployee = new ClientEmployee(employee);
                String json1 = gson.toJson(clientEmployee);
                String json2 = gson.toJson(true);
                json = "["+json1+","+json2+"]";
            }

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
