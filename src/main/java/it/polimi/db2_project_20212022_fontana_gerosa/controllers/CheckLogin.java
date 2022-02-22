package it.polimi.db2_project_20212022_fontana_gerosa.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.User;
import it.polimi.db2_project_20212022_fontana_gerosa.services.UserService;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.ClientUser;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet("/CheckLogin")
@MultipartConfig
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckLogin() {
        super();
    }

    public void init() throws ServletException {
        //TODO initialize persistence connection??
    }

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
        // query db to authenticate for user
        UserService userService = new UserService();
        User user = null;
        try {
            user = userService.checkCredentials(email, password);
        } catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error, retry later");
            return;
        }
        // If the user exists, add info to the session and go to home page, otherwise
        // return an error status code and message
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Incorrect credentials");
        } else {
            ClientUser clientUser = new ClientUser(user);
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(clientUser);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

    public void destroy() {
        try {
            //TODO close persistence connection?
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
