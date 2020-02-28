package by.epam.web.unit6.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "CharsetFilter")
public class CharsetFilter implements Filter {
    private String encoding="UTF-8";
    private ServletContext context;
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        context.log("Charset was set."); //log будет записан в томкат!!!

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        context = config.getServletContext();
    }

}
