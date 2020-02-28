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

public class DeleteUserCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String USERS_LIST = "usersList";
    private final static String DEL_MESSAGE = "deleteUserMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String DEL_MESSAGE_OK = "User deleted!";
    private final static String DEL_MESSAGE_NOT = "User not deleted!";
    private final static String ERROR_MESSAGE_TEXT = "You have no permission for this action! Please, log in! ";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER); //admin
        int id = Integer.parseInt(request.getParameter(ID));
        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (admin.getRole().equals(Role.ADMIN)) {
            try {
                if (userService.deleteUser(id)) {
                    session.setAttribute(DEL_MESSAGE, DEL_MESSAGE_OK);
                    List<User> users = userService.getUsers();
                    session.setAttribute(USERS_LIST, users);
                } else {
                    session.setAttribute(DEL_MESSAGE, DEL_MESSAGE_NOT);
                }

            } catch (ServiceException e) {
                logger.error(e);
            }

        } else if (admin.getRole().equals(Role.USER)) {
            try {
                if (userService.deleteUser(id)) {
                    session.setAttribute(ERROR_MESSAGE, DEL_MESSAGE_OK);
                    session.setAttribute(USER, null);
                    goToPage = JSPPageName.INDEX_PAGE;
                } else {
                    session.setAttribute(ERROR_MESSAGE, DEL_MESSAGE_NOT);
                    goToPage=JSPPageName.ERROR_PAGE;
                }
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
