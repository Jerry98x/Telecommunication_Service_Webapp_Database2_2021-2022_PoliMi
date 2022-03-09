package it.polimi.db2_project_20212022_fontana_gerosa.controllers;


import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.services.UserService;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Registration")
@MultipartConfig
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/UserService")
    private UserService userService = new UserService();

    public Registration() { super();}

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

//    /**
//     * Method to check email validity
//     * @param email email to check
//     * @return true if it's valid, false otherwise
//     */
//    boolean isEmailValid(String email) {
//        return email != null && EmailValidator.getInstance().isValid(email);
//    }
//
//    /**
//     * Method to check username validity
//     * @param username username to check
//     * @return true if it's valid, false otherwise
//     */
//    boolean isUsernameValid(String username) {
//        return username != null && username.length()<32 && username.length() > 3;
//    }
//
//    /**
//     * Method to check username validity
//     * @param password password to check
//     * @return true if it's valid, false otherwise
//     */
//    boolean isPasswordValid(String password){
//        return password != null && password.length()<32 && password.length() > 3;
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            User userToRegister = userService.registerUser(email, username, password);
            response.setStatus(HttpServletResponse.SC_OK);

            //TODO purpose??
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("Welcome, " + userToRegister.getUsername() + "!\nPlease, login.");
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
