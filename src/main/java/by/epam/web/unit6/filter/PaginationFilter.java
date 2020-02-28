package by.epam.web.unit6.filter;

import by.epam.web.unit6.bean.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PaginationFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private static final String USER = "user";
    private static final String PAGE_NUM ="pageNum";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        int pageNum=1;
        logger.info("pagination filter");
        if(session.getAttribute(PAGE_NUM)==null){
            session.setAttribute(PAGE_NUM, pageNum);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
