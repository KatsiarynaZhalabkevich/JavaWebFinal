package by.epam.web.unit6.command.util;

import by.epam.web.unit6.command.CommandName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectForwardMaker {
    private final static String  PATH="?command=";
    private final static String MESSAGE="&message=";

    private RedirectForwardMaker(){}

    public static void forward(HttpServletRequest req, HttpServletResponse resp, String jspPageName) throws ServletException, IOException{
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(jspPageName);

        requestDispatcher.forward(req, resp);
    }

    public static void redirect(HttpServletRequest req, HttpServletResponse resp, CommandName commandName) throws ServletException, IOException {

        String url = req.getRequestURL().toString();

        resp.sendRedirect(url + PATH + commandName);
    }

    public static void redirect(HttpServletRequest req, HttpServletResponse resp, CommandName commandName, String message) throws ServletException, IOException {

        String url = req.getRequestURL().toString();

        System.out.println(message);
        resp.sendRedirect(url + PATH + commandName + MESSAGE + message);
    }
    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {

        String url = req.getRequestURL().toString();

        System.out.println(message);
        resp.sendRedirect(url + MESSAGE + message);
    }
}
