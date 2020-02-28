package by.epam.web.unit6.command;

import by.epam.web.unit6.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CommandTest {
    private final static long serialVersionUID = 1L;
    private final static Logger logger = LogManager.getLogger();
    private final static CommandProvider provider = CommandProvider.getInstance();

    final Controller servlet = new Controller();
    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

/*
    @Test
    public void authorizationCommandTest() throws ServletException, IOException {
       verify(request, times(1)).getRequestDispatcher("");
        verify(request, times(1)).getSession();
        verify(dispatcher).forward(request, response);


    }
*/

}
