package by.epam.web.unit6.controller;

import by.epam.web.unit6.command.Command;
import by.epam.web.unit6.command.CommandProvider;
import by.epam.web.unit6.command.util.RedirectForwardMaker;
import by.epam.web.unit6.tag.TimeJSPTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Единственный сервлет
 */
public class Controller extends HttpServlet {
    private  final static long serialVersionUID = 1L;
    private final static Logger logger = LogManager.getLogger();
    private final static  CommandProvider provider = CommandProvider.getInstance();

    private  final static String COMMAND = "command";
    public Controller() {
        super();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName;
        Command command;

        if(request.getParameter("local")!=null) {
            request.getSession(true).setAttribute("local", request.getParameter("local"));
            if(request.getParameter("local").equals("ru")){
                TimeJSPTag.locale=new Locale("ru");
            }else{
                TimeJSPTag.locale=Locale.ENGLISH;
            }
            logger.info(request.getParameter("local"));
            RedirectForwardMaker.forward(request, response, JSPPageName.INDEX_PAGE);
        }

            commandName = request.getParameter(COMMAND);
            command = provider.getCommand(commandName);
            logger.info(commandName);
            command.execute(request, response);


    }


}
