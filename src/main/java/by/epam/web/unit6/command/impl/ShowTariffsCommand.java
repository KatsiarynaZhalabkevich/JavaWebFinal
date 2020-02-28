package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Role;
import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.TarifService;
import by.epam.web.unit6.service.impl.TarifServiceImpl;
import by.epam.web.unit6.tag.JSPListBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowTariffsCommand implements Command {

    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM ="pageNum";
    private final static String IS_LAST_PAGE = "isLastPage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TarifService tarifService = new TarifServiceImpl();
        List<Tarif> tarifList;

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);

        String goToPage=JSPPageName.ERROR_PAGE;
        logger.info(request.getPathInfo());
        logger.info(request.getServletPath());
        logger.info(request.getRequestURL());

        long page;
        try {

           tarifList= makePageTariff(request);
            if (admin == null || !Role.ADMIN.equals(admin.getRole())) {
                JSPListBean jspListBean = new JSPListBean(tarifList);
                session.setAttribute("userbean", jspListBean);
                goToPage = JSPPageName.TARIF_PAGE;
            } else {
                goToPage = JSPPageName.TARIF_ADMIN_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
        }
        response.sendRedirect(goToPage);
    }

    private List<Tarif> makePageTariff(HttpServletRequest request) throws ServiceException {
        TarifService tarifService = new TarifServiceImpl();
        List<Tarif> tarifList;
        HttpSession session = request.getSession();

        long page;
        if (request.getParameter(PAGE_NUM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUM));
            session.setAttribute(PAGE_NUM, page);
        } else {
            page = 1;
            session.setAttribute(PAGE_NUM, page);
        }
        tarifList = tarifService.showTariffRange((int) page);
        if (tarifList != null) {
            session.setAttribute(TARIFFS, tarifList);
            if (tarifList.size() < 3) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        }else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        return tarifList;

    }
}
