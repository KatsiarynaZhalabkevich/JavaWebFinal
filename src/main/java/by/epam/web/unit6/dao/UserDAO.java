package by.epam.web.unit6.dao;

import by.epam.web.unit6.bean.User;

import java.util.List;

public interface UserDAO {

    User findUserByLogin(String login) throws DAOException;

    boolean addUser(User user) throws DAOException;

    User findUserById(int id) throws DAOException;

    boolean isLoginExist(String login) throws DAOException;

    List<User> takeAllUser() throws DAOException;

    boolean updateUserInfo(User user) throws DAOException;

    boolean deleteUser(int id) throws DAOException;

    int findMaxUserId() throws DAOException;

    String getPasswordById(int id) throws DAOException;

    boolean updatePassword(User user) throws DAOException;

    boolean updateIsActive(boolean active, int id) throws DAOException;

    boolean updateUserBalanceById(int id, double balance) throws DAOException;
    List<User> getUsersRange(int firstPosition, int limit) throws DAOException;



}
