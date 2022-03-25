package it.polimi.db2_project_20212022_fontana_gerosa.controllers;


import it.polimi.db2_project_20212022_fontana_gerosa.entities.User;
import it.polimi.db2_project_20212022_fontana_gerosa.ejbs.UserService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ConnectionHandler;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.DataValidator;
import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import org.apache.commons.text.StringEscapeUtils;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/RegisterUser")
@MultipartConfig
public class RegisterUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.ejbs/UserService")
    private UserService userService = new UserService();

    public RegisterUser() { super();}

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the user is already logged in (present in session) alert
        HttpSession session = request.getSession();
        if (!session.isNew() && session.getAttribute("userId") != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User already logged in. Logout to register a new user.");
            return;
        }

        // obtain and escape params
        String email = null;
        String username = null;
        String password = null;
        String repeatedPassword = null;
        username = StringEscapeUtils.escapeJava(request.getParameter("signup_username"));
        email = StringEscapeUtils.escapeJava(request.getParameter("signup_email"));
        password = StringEscapeUtils.escapeJava(request.getParameter("signup_password"));
        repeatedPassword = StringEscapeUtils.escapeJava(request.getParameter("signup_repeated_password"));

        if (email == null || username == null || password == null || repeatedPassword == null || email.isEmpty() || username.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Credentials must be not null");
            return;
        }

        if(!(DataValidator.isEmailValid(email) && DataValidator.isUsernameValid(username) && DataValidator.isPasswordValid(password))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid username, email or password");
            return;
        }

        // query db to check existing user
        User byEmail = null;
        User byUsername = null;
        try {
            byEmail = userService.findUserByEmail(email);
            byUsername = userService.findUserByUsername(username);
        } catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        // If the user doesn't exist
        // return an error status code and message
        if (byEmail == null && byUsername == null && password.equals(repeatedPassword)) {
            try {
                User userToRegister = userService.registerUser(email, username, password);
            } catch (PersistenceException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error, retry later");
                return;
            }
            
            response.setStatus(HttpServletResponse.SC_OK);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
//            response.getWriter().println("Welcome, " + userToRegister.getUsername() + "!\nPlease, login.");
            response.getWriter().println("Registration succesfully completed!");
        } else if (byEmail != null) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println("Email already in use");
        } else if (byUsername != null) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println("Username already in use");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println("Passwords do not coincide");
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
