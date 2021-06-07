package ua.training.controller.filters;

import ua.training.model.entity.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String path = request.getRequestURI();

        if (!path.contains("reader") && !path.contains("librarian") && !path.contains("admin")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            boolean loggedIn = session != null && session.getAttribute("userLogin") != null && session.getAttribute("role") != null;
            if (loggedIn) {
                HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
                if (!loggedUsers.contains((String) session.getAttribute("userLogin"))) {
                    request.getSession().setAttribute("userLogin", null);
                    request.getSession().setAttribute("role", null);
                    response.sendRedirect("/error/error.jsp");
                }
                Role userRole = (Role) session.getAttribute("role");
                if (userRole == Role.READER && path.contains("reader")) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (userRole == Role.LIBRARIAN && path.contains("librarian")) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (userRole == Role.ADMIN && path.contains("admin")) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.sendRedirect("/error/error.jsp");
                }
            } else {
                response.sendRedirect("/error/noAccess.jsp");
            }
        }
    }

    @Override
    public void destroy() {
    }
}
