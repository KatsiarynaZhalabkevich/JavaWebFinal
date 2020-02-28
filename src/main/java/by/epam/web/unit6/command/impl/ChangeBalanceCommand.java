package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Role;
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
import java.util.List;

public class ChangeBalanceCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String PREV_BALANCE = "old_balance";
    private final static String BALANCE = "balance";
    private final static String USERS_LIST = "usersList";
    private final static String UPD_BALANCE_MESSAGE = "updBalanceMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_BALANCE_MESSAGE_OK="Balance updated!";
    private final static String UPD_BALANCE_MESSAGE_NOT="Can't upd user balance";
    private final static String ERROR_MESSAGE_TEXT="You have no permission for this action! Please, log in! ";

    /**
     * Метод администратора для изменения баланса пользователя
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER); //admin
        int id = Integer.parseInt(request.getParameter(ID));
        double oldBalance = Double.parseDouble(request.getParameter(PREV_BALANCE));
        double balance = oldBalance + Double.parseDouble(request.getParameter(BALANCE));
        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (Role.ADMIN.equals(admin.getRole())) {
            try {
                if (userService.changeBalanceById(id, balance)) {
                    session.setAttribute(UPD_BALANCE_MESSAGE, UPD_BALANCE_MESSAGE_OK);
                    List<User> users = userService.getUsers();//запрашивать всех сразу
                    session.setAttribute(USERS_LIST, users);
                } else {
                    session.setAttribute(UPD_BALANCE_MESSAGE, UPD_BALANCE_MESSAGE_NOT);
                }

            } catch (ServiceException e) {
                logger.error(e);
            }
        } else {
            goToPage = JSPPageName.ERROR_PAGE;
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);

        }
        response.sendRedirect(goToPage);

    }
}
