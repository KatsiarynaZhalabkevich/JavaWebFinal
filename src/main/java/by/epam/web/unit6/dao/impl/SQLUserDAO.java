package by.epam.web.unit6.dao.impl;

import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.bean.util.PasswordCreater;
import by.epam.web.unit6.dao.DAOException;
import by.epam.web.unit6.dao.UserDAO;
import by.epam.web.unit6.pool.ConnectionPool;
import by.epam.web.unit6.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUserDAO implements UserDAO {

    private final static Logger logger = LogManager.getLogger();

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String FIND_USER_BY_LOGIN = "Select * FROM user where login=?;";
    private final static String ADD_USER = "INSERT INTO user (name, surname, phone, email, login, password ) values (?,?,?,?,?,?);";
    private final static String FIND_USER_BY_ID = "SELECT * FROM  user WHERE user.id=?;";
    private final static String IS_LOGIN_EXIST = "SELECT login FROM user WHERE login=?;";
    private final static String TAKE_ALL_USERS = "SELECT * FROM user";
    private final static String FIND_MAX_USER_ID = "SELECT MAX(id) FROM user;";
    private final static String UPD_USER_INFO = "UPDATE user SET name=?, surname=?, phone=?, email=?,login=?, balance=?  WHERE id =?;";
    private final static String UPD_PASS_BY_ID = "UPDATE user SET password=? WHERE id=?;";
    private final static String GET_PASS_BY_ID = "SELECT password FROM user WHERE id=?";
    private final static String DELETE_USER_BY_ID = "DELETE  FROM user WHERE id=?;";
    private final static String UPD_USER_BALANCE_BY_ID = "UPDATE user SET balance=? WHERE id=?";
    private final static String UPD_USER_STATUS_BY_ID = "UPDATE user SET active=? WHERE id=?";
    private static final String GET_USERS_FROM_TO = "SELECT * FROM user LIMIT ? OFFSET ?;";

    private Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLUserDAO() {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, FIND_USER_BY_LOGIN);
            setPreparedStatement(connection, ADD_USER);
            setPreparedStatement(connection, FIND_USER_BY_ID);
            setPreparedStatement(connection, IS_LOGIN_EXIST);
            setPreparedStatement(connection, TAKE_ALL_USERS);
            setPreparedStatement(connection, FIND_MAX_USER_ID);
            setPreparedStatement(connection, UPD_USER_INFO);
            setPreparedStatement(connection, GET_PASS_BY_ID);
            setPreparedStatement(connection, DELETE_USER_BY_ID);
            setPreparedStatement(connection, UPD_PASS_BY_ID);
            setPreparedStatement(connection, UPD_USER_BALANCE_BY_ID);
            setPreparedStatement(connection, UPD_USER_STATUS_BY_ID);
            setPreparedStatement(connection, GET_USERS_FROM_TO);
        } catch (ConnectionPoolException | DAOException e) {
            logger.error(e);

        }


    }

    private User convertResultSet(ResultSet resultSet) throws DAOException {
        User user = new User();
        try {
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setSurname(resultSet.getString(3));
            user.setPhone(resultSet.getString(4));
            user.setEmail(resultSet.getString(5));
            user.setRole(resultSet.getString(6));
            user.setActive(resultSet.getBoolean(7));
            user.setBalance(resultSet.getInt(8));
            user.setTime(resultSet.getTimestamp(9));
            user.setLogin(resultSet.getString(10));
            user.setPassword(resultSet.getString(11));
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return user;
    }


    private void setPreparedStatement(Connection connection, String sql) throws DAOException {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException(e);

            }
        }
    }

    @Override
    public User findUserByLogin(String login) throws DAOException {

        try  {
            PreparedStatement statement = preparedStatementMap.get(FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = convertResultSet(resultSet);
                return user;
            } else {
                logger.info("Login or password is incorrect!");
                return null;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean flag = false;

        try { //не получится объединить строки в отдельный метод, тк формат запросов разный и параметры стоят на разных местах
            PreparedStatement statement = preparedStatementMap.get(ADD_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            if (statement.executeUpdate() == 1) {
                flag = true;
            }


        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with new user");
            throw new DAOException(e);
        }
        return flag;
    }

    @Override
    public User findUserById(int id) throws DAOException {

        try {
            PreparedStatement statement = preparedStatementMap.get(FIND_USER_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = convertResultSet(resultSet);
                return user;
            } else {
                logger.info("User with such id don't exist");
                return null;
            }

        } catch (SQLException e) {
            logger.error("cant get data by id");
            throw new DAOException(e);

        }

    }

    @Override
    public boolean isLoginExist(String login) throws DAOException {
        boolean flag = false;
        try {
            PreparedStatement statement = preparedStatementMap.get(IS_LOGIN_EXIST);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flag = true;
                //такой логин существует => нового пользователя создать нельзя
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return flag;
    }


    @Override
    public List<User> takeAllUser() throws DAOException {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = preparedStatementMap.get(TAKE_ALL_USERS);
            logger.info("dao users");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                logger.info("result set");
                User user = convertResultSet(resultSet);
                users.add(user);
                logger.info("user add");

            }
            logger.info(users);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }

        return users;
    }

    //уже не нужен
    public int findMaxUserId() throws DAOException {
        int id = -1;

        try (PreparedStatement statement = preparedStatementMap.get(FIND_MAX_USER_ID)) {

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return id;
    }

    @Override
    public String getPasswordById(int id) throws DAOException {
        String pas = null;
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_PASS_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                pas = resultSet.getString("password");
            }
        } catch (SQLException e) {
            logger.error("sql cant return pass");
            logger.error(e);
            throw new DAOException(e);
        }
        return pas;
    }

    @Override
    public boolean updatePassword(User user) throws DAOException {
        boolean flag = false;

        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_PASS_BY_ID);

            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());

            if (statement.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException e) {
            logger.error("sql cant upd info");
            logger.error(e);
            throw new DAOException(e);
        }
        return flag;
    }

    public boolean updateUserBalanceById(int id, double balance) throws DAOException {
        boolean flag = false;
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_BALANCE_BY_ID);
            statement.setDouble(1, balance);
            statement.setInt(2, id);
            if (statement.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("Can't update balance!");
        }
        return flag;
    }

    @Override
    public List<User> getUsersRange(int firstPosition, int limit) throws DAOException {
        List<User> users= new ArrayList<>();
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_USERS_FROM_TO);
            statement.setInt(1, limit);
            statement.setInt(2, firstPosition);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = convertResultSet(resultSet);
                users.add(user);

            }
        } catch (SQLException e) {
            logger.error(" can't get all users");
            throw new DAOException(e);
        }
        return users;
    }

    public boolean updateIsActive(boolean active, int id) throws DAOException {
        boolean flag = false;
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_STATUS_BY_ID);
            statement.setBoolean(1, active);
            statement.setInt(2, id);
            if (statement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("can't upd active field");
        }
        return flag;
    }

    @Override
    public boolean updateUserInfo(User user) throws DAOException {
        boolean flag = false;
        //password в этом методе не меняем
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_INFO);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getLogin());
            statement.setDouble(6, user.getBalance());
            statement.setInt(7, user.getId());

            logger.info(statement.executeUpdate()+" int execute UPD");
            if (statement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            logger.error("sql cant upd info");
            logger.error(e);
            throw new DAOException(e);
        }
        return flag;
    }

    @Override
    public boolean deleteUser(int id) throws DAOException {
        boolean flag = false;

        try {
            PreparedStatement statement = preparedStatementMap.get(DELETE_USER_BY_ID);
            statement.setInt(1, id);
            if (statement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return flag;
    }


}
