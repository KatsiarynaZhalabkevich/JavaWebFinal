package by.epam.web.unit6.filter;

import by.epam.web.unit6.bean.Role;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.controller.JSPPageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowUserFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="No permission for this action! Please, log in!";
    private final static String USER = "user";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);


        if (user == null) {
            response.sendRedirect(JSPPageName.ERROR_PAGE);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        } else if (user.getRole().equals(Role.USER)) {
            response.sendRedirect(JSPPageName.USER_AUTH_PAGE);
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
