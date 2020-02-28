package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Role;
import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.TarifService;
import by.epam.web.unit6.service.impl.TarifServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DeleteTarifCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFF_ID = "tarif_id";
    private final static String DELETE_MESSAGE = "deleteMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String TARIFFS = "tarifs";
    private final static String DELETE_MESSAGE_TEXT_OK ="Tariff deleted!";
    private final static String ERROR_MESSAGE_TEXT ="Your session is finished or you don't have permission to delete tariff!";
    private final static String DELETE_MESSAGE_TEXT_NOT ="Tariff wasn't deleted!";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        TarifService tarifService = new TarifServiceImpl();
        HttpSession session = request.getSession(); //сессию создавать не нужно, тк пользователь уже авторизован
        User admin = (User) session.getAttribute(USER);
        int tarifId = Integer.parseInt(request.getParameter(TARIFF_ID));

        String goToPage = JSPPageName.TARIF_ADMIN_PAGE;

        if (admin!=null&&Role.ADMIN.equals(admin.getRole())) {
            try {
                if (tarifService.deleteTarif(tarifId)) {
                    session.setAttribute(DELETE_MESSAGE, DELETE_MESSAGE_TEXT_OK);
                    List<Tarif> tariffs = tarifService.showAllTarif();
                    session.setAttribute(TARIFFS, tariffs);
                } else {
                    session.setAttribute(DELETE_MESSAGE, DELETE_MESSAGE_TEXT_NOT);
                }
                goToPage = JSPPageName.TARIF_ADMIN_PAGE; //перейдем на другую страницу потом

            } catch (ServiceException e) {
                logger.error(e);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.ERROR_PAGE;

        }

        response.sendRedirect(goToPage);
    }
}
