package it.polimi.db2_project_20212022_fontana_gerosa.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet Filter implementation class LoginChecker
 */
public class LandingRedirect implements Filter {
    /**
     * Default constructor.
     */
    public LandingRedirect() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException

    {
        System.out.print("Login checker filter executing ...\n");

        // java.lang.String loginpath = "/index.html";
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String loginpath = req.getServletContext().getContextPath() + "/LoginPage.html";

        HttpSession s = req.getSession();
        if (s.isNew() || s.getAttribute("user") == null) {
            res.sendRedirect(loginpath);
            return;
        }

        chain.doFilter(request, response);
    }


    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }
}


