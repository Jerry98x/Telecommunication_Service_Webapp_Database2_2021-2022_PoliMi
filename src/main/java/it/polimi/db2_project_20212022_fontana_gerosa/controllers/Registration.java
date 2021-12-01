package it.polimi.db2_project_20212022_fontana_gerosa.controllers;


import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.services.UserService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;

@WebServlet("/Registration")
@MultipartConfig
public class Registration {
    private static final long serialVersionUID = 1L;

    public Registration() { super();}

    public void init(){
        //TODO
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // obtain and escape params
        String email = null;
        String username = null;
        String password = null;
        String repeatedPassword = null;
        email = StringEscapeUtils.escapeJava(request.getParameter("email"));
        username = StringEscapeUtils.escapeJava(request.getParameter("username"));
        password = StringEscapeUtils.escapeJava(request.getParameter("password"));
        repeatedPassword = StringEscapeUtils.escapeJava(request.getParameter("repeated_password"));
        if (email == null || username == null || password == null || email.isEmpty() || username.isEmpty() || password.isEmpty() ) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Credentials must be not null");
            return;
        }
        // query db to authenticate for user
        UserService userService = new UserService();
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
        // If the user exists, add info to the session and go to home page, otherwise
        // return an error status code and message
        if (byEmail == null && byUsername == null && password.equals(repeatedPassword)) {
            User userToRegister = userService.registerUser(email, username, password);
            request.getSession().setAttribute("user", userToRegister);
            response.setStatus(HttpServletResponse.SC_OK);

            //TODO purpose??
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(email);
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
}
