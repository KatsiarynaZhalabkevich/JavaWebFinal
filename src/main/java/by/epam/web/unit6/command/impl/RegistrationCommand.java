package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//Нет смысла в этой команде. Со стартовой страницы просто переходим на страницу jsp, данные никакие не передаются
        HttpSession session = request.getSession(true);
        //любой может зарегистрироваться
        String goToPage = JSPPageName.USER_REG_PAGE;
        response.sendRedirect(goToPage);


    }
}



