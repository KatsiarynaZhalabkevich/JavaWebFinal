package by.epam.web.unit6.dao;

import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.service.ServiceProvider;
import by.epam.web.unit6.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoTest {
    private final static Logger logger = LogManager.getLogger();
    UserDAO userDAO = DAOProvider.getInstance().getUserDao();

    private static User userTest;
    private static String passwordTest;
    private static String loginTest;
    @BeforeClass
    public static void createUserForTest() {
        User user = new User();

        String name = "User";
        String surname = "Test";
        String phone = "+375291234567";
        String email = "usertest@mail.ru";

        String login = "LoginTest";
        String password = "Pass1234";

        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);

        userTest = user;
        passwordTest = password;
        loginTest = login;
    }
 /*   @Test
    public void updatePasswordTest(){
        boolean isPassUpdated=false;

        try {
            userDAO.addUser(userTest);
            String password="Pass123456";
            userTest.setPassword(password);
           isPassUpdated= userDAO.updatePassword(userTest);
        } catch (DAOException e) {
            logger.error(e);
        }
    }*/
}
