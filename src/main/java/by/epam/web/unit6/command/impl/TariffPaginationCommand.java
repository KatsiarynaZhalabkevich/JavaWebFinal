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

public class TariffPaginationCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get information about tariffs. Please, try later";
    private static final String PAGE_NUM = "pageNum";
    private static final String IS_LAST_PAGE = "isLastPage";
    private static final int SIZE =3 ;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        TarifService tarifService = new TarifServiceImpl();
        List<Tarif> tarifList = null;

        HttpSession session = request.getSession(true);
        long page;
        String goToPage = (String) session.getAttribute("goToPage");


        if (session.getAttribute(PAGE_NUM) != null) {
            page = (Long) session.getAttribute(PAGE_NUM);

        } else {
            page=1;
            session.setAttribute(PAGE_NUM, page);
        }

        try {
            tarifList = tarifService.showTariffRange((int) page);
        } catch (ServiceException e) {
            logger.error(e);
        }


        if (tarifList != null) {
            session.setAttribute(TARIFFS, tarifList);
            if (tarifList.size() < SIZE) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.ERROR_PAGE;
        }

        response.sendRedirect(goToPage);
    }
}
