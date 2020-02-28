package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.ServiceProvider;
import by.epam.web.unit6.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class UpdateBalanceCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String BALANCE = "balance";
    private final static String UPD_BALANCE_MESS = "updBalanceMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_BALANCE_MESS_OK="Balance updated!";
    private final static String UPD_BALANCE_MESS_NOT="Can't upd user balance";
    private final static String ERROR_MESSAGE_TEXT="Can't upd user balance. Please, sign in and try again";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER); //по ключу получаем значение
        String goToPage = JSPPageName.USER_AUTH_PAGE;
        double balance = Double.parseDouble(request.getParameter(BALANCE));

        if (user != null) {
            user.setBalance(balance);
            try {
                if (userService.saveUpdateUser(user)) {
                    session.setAttribute(UPD_BALANCE_MESS, UPD_BALANCE_MESS_OK);
                    session.setAttribute(USER, user);
                }else{
                    session.setAttribute(UPD_BALANCE_MESS, UPD_BALANCE_MESS_NOT);
                }
            } catch (ServiceException e) {
                logger.error(e);

            }
        }else{
            goToPage=JSPPageName.ERROR_PAGE;
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT );
        }
        response.sendRedirect(goToPage);


    }
}
