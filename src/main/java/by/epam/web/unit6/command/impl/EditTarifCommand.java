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

public class EditTarifCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFF_ID="tarif_id";
    private final static String NAME="name";
    private final static String DESCRIPTION ="description";
    private final static String SPEED = "speed";
    private final static String PRICE = "price";
    private final static String DISCOUNT = "discount";
    private final static String EDIT_MESSAGE = "editMessage";
    private final static String ERROR_MESSAGE="errorMessage";
    private final static String TARIFFS = "tarifs";
    private final static String EDIT_MESSAGE_TEXT_OK="Tariff updated!";
    private final static String EDIT_MESSAGE_TEXT_NOT="Tariff not updated!";
    private final static String ERROR_MESSAGE_TEXT="You haven't permission for this action!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        TarifService tarifService = new TarifServiceImpl();
        HttpSession session = request.getSession(); //сессию создавать не нужно, тк пользователь уже авторизован
        User admin = (User) session.getAttribute(USER);

        int id = Integer.parseInt(request.getParameter(TARIFF_ID).trim());
        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        int speed = Integer.parseInt(request.getParameter(SPEED).trim());
        double price = Double.parseDouble(request.getParameter(PRICE).trim());
        double discount = Double.parseDouble(request.getParameter(DISCOUNT).trim());

        Tarif tarif = new Tarif();
        tarif.setId(id);
        tarif.setSpeed(speed);
        tarif.setPrice(price);
        tarif.setDiscount(discount);
        tarif.setDescription(description);
        tarif.setName(name);


        String goToPage=JSPPageName.TARIF_ADMIN_PAGE;
        if (Role.ADMIN.equals(admin.getRole())) {
            try {
                if (tarifService.changeTarif(tarif)) {
                    session.setAttribute(EDIT_MESSAGE, EDIT_MESSAGE_TEXT_OK);
                    List<Tarif> tariffs = tarifService.showAllTarif();
                    session.setAttribute(TARIFFS, tariffs);
                } else {
                    session.setAttribute(EDIT_MESSAGE, EDIT_MESSAGE_TEXT_NOT);
                }
                goToPage = JSPPageName.TARIF_ADMIN_PAGE;
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
