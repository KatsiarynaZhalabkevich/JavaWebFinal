package by.epam.web.unit6.service.impl;

import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.bean.util.PasswordCreater;
import by.epam.web.unit6.dao.DAOException;
import by.epam.web.unit6.dao.DAOProvider;
import by.epam.web.unit6.dao.UserDAO;
import by.epam.web.unit6.service.ServiceException;
import by.epam.web.unit6.service.UserService;
import by.epam.web.unit6.service.validation.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();
    private static final UserDataValidator validator = UserDataValidator.getInstance();
    private static final UserDAO userDao = DAOProvider.getInstance().getUserDao();

    /**
     *
     * @param login
     * @param password
     * @return
     * @throws ServiceException
     */
    @Override
    public User authorization(String login, String password) throws ServiceException {
        //проверка корректности ввода
        if (login == null || login == "" || password == "" || password == null) {

            logger.error("Login or password = null");
            throw new ServiceException("Incorrect login or password!");
        }

        User user;
        try {
            user = userDao.findUserByLogin(login);
            if (PasswordCreater.verifyPassword(password, user.getPassword())) {
                return user;
            } else{
                logger.info("Password is incorrect ");
                return null;
            }

        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't get user with such login or password"); //чтобы на странице ошибок не поняли, что случилось
        }


    }

    /**
     * Один метод на два действия (сохранение нового пользователя и обновление существующего), т.к. алгоритм действий схожий DRY
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean saveUpdateUser(User user) throws ServiceException {
        boolean flag;
        String login = user.getLogin();
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        String phone = user.getPhone();
        String email = user.getEmail();
        double balance = user.getBalance();
//как map передать на страницу????
        Map<String, String> errorMap = new HashMap();

        if (!validator.checkLogin(login)) {
            errorMap.put("login", "Incorrect login or password format!");
            throw new ServiceException("Incorrect login or password format!");
        }
        if (password != null && !validator.checkPassword(password)) { //если password=null, то мы ничего не обновляли
            errorMap.put("password", "Incorrect login or password format!");
            throw new ServiceException("Incorrect login or password format!");
        }
        if (!validator.checkName(name)) {
            errorMap.put("name", "Incorrect name format");
            throw new ServiceException("Incorrect name format");
        }
        if (!validator.checkName(surname)) {
            errorMap.put("surname", "Incorrect surname format");
            throw new ServiceException("Incorrect surname format");
        }
        if (!validator.checkPhone(phone)) {
            errorMap.put("phone", "Incorrect phone number format");
            throw new ServiceException("Incorrect phone number format");
        }
        if (!validator.checkEmail(email)) {
            errorMap.put("email", "Incorrect email format!");
            throw new ServiceException("Incorrect email format!");
        }
        if (balance < 0) {
            errorMap.put("balance", "Incorrect balance value!");
            throw new ServiceException("Incorrect balance value!");
        }
//разница между update and sAVe
        try {
            if (userDao.isLoginExist(login)) { //если такой логин есть, то это операция update
                flag = userDao.updateUserInfo(user);
                logger.info("update name "+flag);
                if (password != null) {//вызвать изменение пароля
                    String newPas = PasswordCreater.createPassword(password);
                    user.setPassword(newPas);
                    flag = userDao.updatePassword(user);
                    logger.info("update pass "+flag);
                }

            } else {  //если логина нет, то это создание нового пользователя
                String newPass = PasswordCreater.createPassword(password);
                user.setPassword(newPass);
                flag = userDao.addUser(user);

            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't save or update user's information. Please, try later.");
        }
        return flag;
    }

    /**
     *
     * @param login
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean isLoginUniq(String login) throws ServiceException {
        boolean flag;
        //не проверяем логин, тк если он неверный, то просто такого пользователя не будет
        if (login == null) {
            logger.error("Login is null");
            throw new ServiceException("Login field is empty!");
        } else {
            try {
                flag = userDao.isLoginExist(login);
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException("Can't get data. Please, try again letter.");
            }
            return flag;
        }
    }

    /**
     * Метод получения списка всех зарегистрированных пользователей
     * @return
     * @throws ServiceException
     */
    @Override
    public List<User> getUsers() throws ServiceException {
        List<User> users = new ArrayList<>();
        logger.info("service users");
        try {
            logger.info("start add users");
            users.addAll(userDao.takeAllUser());
            logger.info("get users", users);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't get users! Please try again letter.");
        }
        return users;
    }

    /**
     * Удаление пользователя по id
     * @param id
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean deleteUser(int id) throws ServiceException {
        boolean flag;
        try {
            flag = userDao.deleteUser(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't delete user! Please try again letter.");
        }
        return flag;
    }

    /**
     * Метод изменения баланса для администратора,
     * т.к. здесь учтена возможность понижения баланса
     * (у пользователя есть другой метод, где можно только повысить баланс )
     * @param id
     * @param balance
     * @return
     * @throws ServiceException
     */
    //метод для админа, он может понижать баланс!
    public boolean changeBalanceById(int id, double balance) throws ServiceException {
        boolean flag;
        if (balance > 100) {
            throw new ServiceException("Incorrect balance value!");
        }

        try {
            flag = userDao.updateUserBalanceById(id, balance);
        } catch (DAOException e) {
            logger.error("Can't upd balance");
            throw new ServiceException(e);
        }
        return flag;
    }

    /**
     * Метод для администратора, чтобы заблокировать или разблокировать пользователя
     * @param id
     * @param active
     * @return
     * @throws ServiceException
     */
    public boolean changeStatusById(int id, boolean active) throws ServiceException {
        boolean flag;


        try {
            flag = userDao.updateIsActive(active, id);
        } catch (DAOException e) {
            logger.error("Can't upd status serv");
            throw new ServiceException(e);

        }
        return flag;
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        User user;
        try {
            user=userDao.findUserById(id);
        }catch(DAOException e){
            logger.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> getUsersRange(long page) throws ServiceException {
        List<User> users;
        try {
            int limit = 3;
            int firstPosition = (int)page * limit-limit;
            users = userDao.getUsersRange(firstPosition, limit);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("problems with dao");
        }
        return users;
    }


}
