package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.bean.Role;
import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.command.Command;
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
import java.util.ArrayList;
import java.util.List;

public class PaymentCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String PAYMENT_MESSAGE = "paymentMessage";
    private final static String USERS_LIST = "usersList";
    private final static String PAYMENT_MESSAGE_TEXT="Payment OK";
    private final static String ERROR_MESSAGE_TEXT_PAY="Can't get users for payment!";
    private final static String ERROR_MESSAGE_TEXT= "Session is not valid!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = ServiceProvider.getInstance().getUserService();
        TarifService tarifService = ServiceProvider.getInstance().getTarifService();
        List<UserTarif> userTariffs = new ArrayList<>();
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        List<User> userList = new ArrayList<>();
        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (admin != null && admin.getRole().equals(Role.ADMIN)) {
            //получаем всех юзеров
            try {
                userList.addAll(userService.getUsers());
            } catch (ServiceException e) {
                logger.error(e);
            }

            if (userList.size() != 0) {
                for (User user : userList) {
                    try {
                        userTariffs = tarifService.showTarifsByUserId(user.getId());
                    } catch (ServiceException e) {
                        logger.error(e);
                    }
                    if (userTariffs != null) {
                        double payment = paymentToWithdraw(userTariffs);
                        try {
                            if (!userService.changeBalanceById(user.getId(), payment)) {
                                logger.error(user.getId() + PAYMENT_MESSAGE_TEXT);
                            }

                        } catch (ServiceException e) {
                            logger.error(e);
                        }
                    }
                }
                session.setAttribute(PAYMENT_MESSAGE, PAYMENT_MESSAGE_TEXT);
                //надо получить всех юзеров заново с новой информацией
                try {
                    userList = new ArrayList<>();
                    userList.addAll(userService.getUsers());
                    session.setAttribute(USERS_LIST, userList);
                } catch (ServiceException e) {
                    logger.error(e);
                }

            } else {
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT_PAY);
                goToPage = JSPPageName.ERROR_PAGE;
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.ERROR_PAGE;
        }
        response.sendRedirect(goToPage);
    }

    private double paymentToWithdraw(List<UserTarif> userTariffs) {
        double payment = 0;
        for (UserTarif tarif : userTariffs) {
            payment -= tarif.getPrice() - tarif.getPrice() * tarif.getDiscount() * 0.01;
        }
        return payment;
    }
}
