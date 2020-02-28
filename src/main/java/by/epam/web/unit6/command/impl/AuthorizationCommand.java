package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Role;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.command.util.Pagination;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.dto.UserTarif;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.ServiceProvider;
import by.epam.web.unit6.service.TarifService;
import by.epam.web.unit6.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class AuthorizationCommand implements Command {

    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTarifs";
    private final static String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
    private final static String LOGIN_ERROR_MESSAGE_TEXT = "Wrong login and/or password!";
    private final static String ERROR_MESSAGE_TEXT = "Can't get data about tariffs!";

    private final static Logger logger = LogManager.getLogger();

    /**
     * Метод для авторизации пользователя. Включает в себя получение списка тарифов пользователя
     * и всех тарифов для отображения на странице пользователя
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login;
        String password;
        login = request.getParameter(LOGIN);
        password = request.getParameter(PASSWORD);

        UserService userService = ServiceProvider.getInstance().getUserService();
        TarifService tarifService = ServiceProvider.getInstance().getTarifService();

        HttpSession session = request.getSession(true);

        User user = null;
        String goToPage;

        try {
            user = userService.authorization(login, password);
        } catch (ServiceException e) {
            logger.error(e);

        }

        if (user != null) {
            session.setAttribute(USER, user);

            try {
                List<UserTarif> tariffs = tarifService.showTarifsByUserId(user.getId()); //все тарифы какие есть
                session.setAttribute(USER_TARIFFS, tariffs);
                Pagination.makePage(request); //все работает, но может что-то изменить?
//может просто оюратиться к серивису и взять тарифы, их часть как было раньше
            } catch (ServiceException e) {
                logger.error(e);   //никуда не переходим, тк это не главное в данной команде
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            }
            if (Role.ADMIN.equals(user.getRole())) {
                goToPage = JSPPageName.ADMIN_PAGE;

            } else {
                goToPage = JSPPageName.USER_AUTH_PAGE;

            }

        } else {

            session.setAttribute(LOGIN_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.INDEX_PAGE;

        }
        //response.sendRedirect(goToPage);
        request.getRequestDispatcher(goToPage).forward(request, response);

    }

}

