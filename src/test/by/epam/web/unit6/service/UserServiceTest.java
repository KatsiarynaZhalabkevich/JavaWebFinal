package by.epam.web.unit6.service;

import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.service.ServiceProvider;
import by.epam.web.unit6.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class UserServiceTest {

    private final static Logger logger = LogManager.getLogger();
    UserService userService = ServiceProvider.getInstance().getUserService();


    private static User user = new User();

    @BeforeClass
    public static void createUserForTest() {


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


    }

    @Test
    public void saveUserTest() {
        boolean isSaved = false;
        User userBD;

        User userTest = user;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            isSaved = userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            userService.deleteUser(userBD.getId());

        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertTrue(isSaved);

    }

    @Test //некоторые поля получают свои значения при сохранении в БД, т.е. нельзя просто проверить объекты
    public void authorizationTest() {
        User userBD = null;
        User userTest = user;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            userService.deleteUser(userBD.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertEquals(userTest.getLogin(), userBD.getLogin());
        Assert.assertEquals(userTest.getName(), userBD.getName());
        Assert.assertEquals(userTest.getSurname(), userBD.getSurname());
    }

    @Test
    public void updateUserTest() {
        boolean isUpdated = true;
        User userBD;
        String surname = "Updsurname";
        String password = "Pass123456";
        User userTest = user;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            userTest.setId(userBD.getId());
            userTest.setSurname(surname);
            userTest.setPassword(password);
            isUpdated = userService.saveUpdateUser(userTest);
            userService.deleteUser(userBD.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }
        logger.info(isUpdated);
        Assert.assertTrue(isUpdated);
    }

    @Test
    public void deleteUserTest() {
        boolean isDeleted = false;
        User userBD;
        User userTest = user;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            isDeleted = userService.deleteUser(userBD.getId());

        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertTrue(isDeleted);

    }

    @Test //как сделать тест универсальным? сейчас ок, а когда добавим пользователей?
    //
    public void getUsersTest() {
        List<User> list = new ArrayList<>();
        try {
            list = userService.getUsers();
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertEquals(list.size(), list.size());
    }

    @Test
    public void getUserByIdTest() {
        User userBD = null;
        User userById = null;
        User userTest = user;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            userById = userService.getUserById(userBD.getId());
            userService.deleteUser(userBD.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertEquals(userBD, userById);

    }

    @Test
    public void changeBalanceByIdTest() {
        boolean isChanged = false;
        double balance = 13.5;
        User userTest = user;
        User userBD;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            isChanged = userService.changeBalanceById(userBD.getId(), balance);
            userService.deleteUser(userBD.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertTrue(isChanged);
    }

    @Test
    public void changeStatusById(){
        boolean isChanged = false;
        boolean status = true;
        User userTest = user;
        User userBD;
        String passwordTest = user.getPassword();
        String loginTest = user.getLogin();
        try {
            userService.saveUpdateUser(userTest);
            userBD = userService.authorization(loginTest, passwordTest);
            isChanged = userService.changeStatusById(userBD.getId(), status);
            userService.deleteUser(userBD.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertTrue(isChanged);
    }


}
