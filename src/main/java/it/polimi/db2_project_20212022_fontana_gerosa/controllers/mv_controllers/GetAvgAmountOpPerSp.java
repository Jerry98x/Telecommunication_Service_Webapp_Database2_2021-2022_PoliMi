package it.polimi.db2_project_20212022_fontana_gerosa.controllers.mv_controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.mv.MVAvgAmountOpPerSp;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.mv.MVTotalPurchasesPerSp;
import it.polimi.db2_project_20212022_fontana_gerosa.services.MVService;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/GetAvgAmountOpPerSp")
@MultipartConfig
public class GetAvgAmountOpPerSp extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection = null;

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/MVService")
    private MVService mvService = new MVService();

    public GetAvgAmountOpPerSp() { super(); }

    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If the user is not logged in (not present in session) redirect to the login
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("employeeId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("User not allowed");
            return;
        }

        List<MVAvgAmountOpPerSp> mvAvgAmountOpPerSps;

        try {
            mvAvgAmountOpPerSps = mvService.getAllAvgAmountOpPerSp();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Not possible to recover optional products");
            return;
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(mvAvgAmountOpPerSps);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
