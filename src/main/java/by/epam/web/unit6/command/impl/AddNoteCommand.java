package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Note;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;
import by.epam.web.unit6.dto.UserTarif;
import by.epam.web.unit6.service.NoteService;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.ServiceProvider;
import by.epam.web.unit6.service.TarifService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AddNoteCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFF_ID = "tarif_id";
    private final static String UPD_MESSAGE = "updateMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTarifs";

    /**
     * Метод для добавления новой записи о тарифе пользователя
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        NoteService noteService = ServiceProvider.getInstance().getNoteService();
        TarifService tarifService = ServiceProvider.getInstance().getTarifService();
        HttpSession session = request.getSession(); //только зарегистрированный пользователь может добавить себе тариф
        User user = (User) session.getAttribute(USER);
        int tarifId = Integer.parseInt(request.getParameter(TARIFF_ID));

        String goToPage = JSPPageName.USER_AUTH_PAGE;


        if (user != null) {
            Note note = new Note();
            note.setTarifId(tarifId);
            note.setUserId(user.getId());

            try {
                if (noteService.addNote(note)) {//добавили в БД
                    request.setAttribute(UPD_MESSAGE, "New tariff is added!");
                    List<UserTarif> tariffs = tarifService.showTarifsByUserId(user.getId());
                    session.setAttribute(USER_TARIFFS, tariffs);
                } else {
                    request.setAttribute(UPD_MESSAGE, "Can't add tariff to account!");
                }

            } catch (ServiceException e) {
                logger.error(e);
            }

        } else {
            request.setAttribute(ERROR_MESSAGE, "Session is expired. Please, sign in!");
            goToPage = JSPPageName.ERROR_PAGE;
            response.sendRedirect(goToPage);
        }
        response.sendRedirect(goToPage);


    }
}
