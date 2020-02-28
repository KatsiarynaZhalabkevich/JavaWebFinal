package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Note;
import by.epam.web.unit6.bean.Role;
import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.dto.UserTarif;
import by.epam.web.unit6.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String USER_TARIFFS = "userTarifList";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String KONKR_USER = "userName";
    private final static String ERROR_MESSAGE_TEXT = "Can't get user's tariffs";
    private final static String ERROR_MESSAGE_TEXT2 = "You have no permission for this action! Please, log in! ";
    private final static String TARIFFS = "tarifs";
    private final static String USERS_LIST = "usersList";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        TarifService tarifService = ServiceProvider.getInstance().getTarifService();
        NoteService noteService = ServiceProvider.getInstance().getNoteService();

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String goToPage = JSPPageName.ADMIN_PAGE;
        Map<Integer, Integer> tariffsCount = new HashMap<>();


        if (Role.ADMIN.equals(admin.getRole())) {

            try {
                List<User> users = userService.getUsers();

                List<Tarif> tariffs = tarifService.showAllTarif();

                int count = 0;
                for (Tarif t : tariffs) {
                    List<Note> notes = noteService.findNoteByTarifId(t.getId());
                    count += notes.size();
                    tariffsCount.put(t.getId(), notes.size());

                }

                session.setAttribute("user_number", users.size());
                session.setAttribute("tariff_number", tariffs.size());
                session.setAttribute("tariff_count", tariffsCount);
                session.setAttribute("connectionsCount", count);
                session.setAttribute(TARIFFS, tariffs);

                goToPage = JSPPageName.STATISTIC_PAGE;

            } catch (ServiceException e) {
                logger.error(e);
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT2);
            goToPage = JSPPageName.ERROR_PAGE;
        }

        // response.sendRedirect(goToPage);
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
