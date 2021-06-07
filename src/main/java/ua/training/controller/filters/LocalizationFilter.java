package ua.training.controller.filters;

import javax.servlet.*;
import java.io.IOException;
import java.util.Locale;

public class LocalizationFilter implements Filter {
    public static Locale locale;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  throws IOException, ServletException {
        String lang = request.getParameter("language");
        if (lang == null || lang.equals("") || lang.equals("en")) {
            locale = new Locale("en");
        } else {
            locale = new Locale("ua");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
