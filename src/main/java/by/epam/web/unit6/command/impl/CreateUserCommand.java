package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
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
import java.util.Objects;

public class CreateUserCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String LOGIN = "login";
    private final static String PASS = "password";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_LOGIN_MESSAGE = "errorLoginMessage";
    private final static String ERROR_PASSWORD_MESSAGE = "errorPasswordMessage";
    private final static String ERROR_MESSAGE_TEXT_EMPTY = "Some fields are empty!";
    private final static String ERROR_MESSAGE_TEXT = "Can't register a new user. Please, try again.";
    private final static String ERROR_LOGIN_MESSAGE_TEXT = "Login already exist!";
    private final static String ERROR_PASSWORD_MESSAGE_TEXT = "Passwords are not equal!!!";

    /**
     * Метод для создания пользователя
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String phone = request.getParameter(PHONE);
        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password1 = request.getParameter(PASS + 1);
        String password2 = request.getParameter(PASS + 2);

//используем юзера на случай, если данные не будут введены полностью
        User user = new User();
        user.setLogin(login);
        user.setPassword(password1);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);


        UserService userService = ServiceProvider.getInstance().getUserService();
        TarifService tarifService = ServiceProvider.getInstance().getTarifService();

        String goToPage;


        if (!Objects.equals(name, "") && !surname.equals("") && !phone.equals("") && !email.equals("") && !login.equals("")) {
            if (!password1.equals("") && !password1.equals(" ") && password1.equals(password2)) {
                try {
                    if (!userService.isLoginUniq(login)) { //если false, то юзера нету и можно создать нового с этим логином

                        if (userService.saveUpdateUser(user)) { //user создался
                            session = request.getSession(true);
                            session.setAttribute(USER, user);
                            logger.info(user);
                            List<Tarif> tarifList = tarifService.showAllTarif(); //чтобы на страницу подгрузить тарифы, личных пока нету
                            session.setAttribute(TARIFFS, tarifList);
                            goToPage = JSPPageName.USER_AUTH_PAGE;
                        } else {
                            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                            goToPage = JSPPageName.ERROR_PAGE;
                        }

                    } else {
                        session.setAttribute(ERROR_LOGIN_MESSAGE, ERROR_LOGIN_MESSAGE_TEXT);
                        user.setLogin(null);
                        session.setAttribute(USER, user);
                        goToPage = JSPPageName.USER_REG_PAGE;
                    }
                } catch (ServiceException e) {
                    logger.error(e);
                    session.setAttribute(ERROR_MESSAGE, e);
                    session.setAttribute(USER, user);
                    goToPage = JSPPageName.USER_REG_PAGE;

                }
            } else {
                request.setAttribute(ERROR_PASSWORD_MESSAGE, ERROR_PASSWORD_MESSAGE_TEXT);
                session.setAttribute(USER, user);
                goToPage = JSPPageName.USER_REG_PAGE;

            }


        } else {
            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT_EMPTY);
            session.setAttribute(USER, user);
            goToPage = JSPPageName.USER_REG_PAGE;
        }

        //  response.sendRedirect(goToPage);
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
