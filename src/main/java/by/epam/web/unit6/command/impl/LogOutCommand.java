package by.epam.web.unit6.command.impl;

import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.controller.JSPPageName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommand implements Command {
    private final static String USER="user";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute(USER);
        session.invalidate();
        response.sendRedirect(JSPPageName.INDEX_PAGE);
    }
}
