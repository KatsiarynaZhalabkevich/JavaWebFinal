package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.User;

import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.ServiceProvider;
import by.epam.web.unit6.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class UpdateUserCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String PASS = "password";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_MESSAGE = "updateMessage";
    private final static String ERROR_PASSWORD_MESSAGE = "errorPasswordMessage";
    private final static String UPD_MESSAGE_TEXT="User's info updated successfully!";
    private final static String ERROR_PASSWORD_MESSAGE_TEXT="Passwords are not equals";
    private final static String ERROR_MESSAGE_TEXT="Can't update user's information.";
    private final static String ERROR_MESSAGE_TEXT_NOT_USER="Session is finished. You have no permission for this action. Please, log in!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = ServiceProvider.getInstance().getUserService();
        HttpSession session = request.getSession(); //сессию создавать не нужно, тк пользователь уже авторизован
        User user = (User) session.getAttribute(USER);

        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String phone = request.getParameter(PHONE);
        String email = request.getParameter(EMAIL);

        String password1 = request.getParameter(PASS + 1);
        String password2 = request.getParameter(PASS + 2);

        String goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;

        if (user != null) {
            user.setName(name);
            user.setSurname(surname);
            user.setPhone(phone);
            user.setEmail(email);

            try {
                if (userService.saveUpdateUser(user)) {
                    session.setAttribute(USER, user);
                    goToPage = JSPPageName.USER_AUTH_PAGE;
                    session.setAttribute(UPD_MESSAGE, UPD_MESSAGE_TEXT);
                }else{
                    //может надо сделать ошибку на стариницу редактирования
                    session.setAttribute(UPD_MESSAGE, ERROR_MESSAGE_TEXT);
                    goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
                }

            } catch (ServiceException e) {
                logger.error(e);

            }
            if (!Objects.equals(password1, "")) {

                if (password1 != null&&!password1.isEmpty() && password1.equals(password2)) {

                    user.setPassword(password1);
                    try {

                        if (userService.saveUpdateUser(user)) {
                            session.setAttribute(UPD_MESSAGE, UPD_MESSAGE_TEXT);
                            session.setAttribute(USER, user);
                            goToPage = JSPPageName.USER_AUTH_PAGE;
                        }else{
                            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                            goToPage = JSPPageName.ERROR_PAGE;
                        }
                    } catch (ServiceException e) {
                        logger.error(e);
                    }
                } else if (!password1.equals(password2)) {
                    session.setAttribute(ERROR_PASSWORD_MESSAGE, ERROR_PASSWORD_MESSAGE_TEXT);
                    goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
                }
            }
        } else {
            session.setAttribute(ERROR_MESSAGE,ERROR_MESSAGE_TEXT_NOT_USER );
            goToPage = JSPPageName.ERROR_PAGE;
        }

       response.sendRedirect(goToPage);
    }
}




