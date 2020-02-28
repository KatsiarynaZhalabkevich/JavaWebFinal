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

public class DeleteNoteCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String NOTE_ID="note_id";
    private final static String ERROR_MESSAGE="errorMessage";
    private final static String UPD_MESSAGE="updateMessage";
    private final static String USER_TARIFFS = "userTarifs";
    private final static String ERROR_MESSAGE_TEXT="Can't delete tariff from account!";
    private final static String ERROR_MESSAGE_NOT_USER="Session is expired. Please, sign in!";
    private final static String UPD_MESSAGE_TXT="Tariff is deleted!!";


    /**
     * Метод для удаления записи о тарифе пользователя
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        NoteService noteService = ServiceProvider.getInstance().getNoteService();
        TarifService tarifService=ServiceProvider.getInstance().getTarifService();
        HttpSession session = request.getSession(); //только зарегистрированный пользователь может удалить себе тариф
        User user = (User) session.getAttribute(USER);
        int noteId = Integer.parseInt(request.getParameter(NOTE_ID));

        String goToPage;

        if(user!=null){
            try {
                if(noteService.deleteNote(noteId)){
                    session.setAttribute(UPD_MESSAGE, UPD_MESSAGE_TXT);
                    List<UserTarif> tariffs = tarifService.showTarifsByUserId(user.getId());
                    session.setAttribute(USER_TARIFFS, tariffs);
                }else{
                    session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                }
                goToPage = JSPPageName.USER_AUTH_PAGE;

            } catch (ServiceException e) {
               logger.error(e);
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.USER_AUTH_PAGE;

            }
        }else{
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_NOT_USER);
            goToPage = JSPPageName.ERROR_PAGE;
        }
        response.sendRedirect(goToPage);
    }
}
