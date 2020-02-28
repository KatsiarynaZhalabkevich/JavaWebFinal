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

public class EditProfileFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="No permission for this action! Please, log in!";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        logger.info("внутри фильтра по редактированию профиля");

        if (user == null) {

            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        }else{
            request.getRequestDispatcher(JSPPageName.USER_EDIT_PROFILE_PAGE);
        }
        chain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
