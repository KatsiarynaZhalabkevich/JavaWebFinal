package by.epam.web.unit6.main;

import by.epam.web.unit6.bean.User;
import by.epam.web.unit6.bean.util.PasswordCreater;
import by.epam.web.unit6.pool.ConnectionPool;
import by.epam.web.unit6.service.validation.UserDataValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class runner {

    public static void main(String[] args) {
        String password = "Kate0510";
        String newpas = PasswordCreater.createPassword(password);
        System.out.println(newpas);

        String admin = "admin";
        String user = "user";
        String login = "login";
        String kate0510 = "kate0510";

        System.out.println(admin + "       " + PasswordCreater.createPassword(admin));
        System.out.println(user + "       " + PasswordCreater.createPassword(user));
        System.out.println(login + "      " + PasswordCreater.createPassword(login));
        System.out.println(kate0510 + "     " + PasswordCreater.createPassword(kate0510));



    /*
        String sql = "Select user_card.id, " +
                            "user_card.name, " +
                            "user_card.surname, " +
                            "user_card.phone, " +
                            "user_card.email, " +
                            "user_card.role, " +
                            "user_card.active, " +

                            "user_card.balance, " +
                            "user_card.create_time, " +
                            "user.login " +
                    "FROM mytelecom.user_card, mytelecom.user where user.login='admin'AND user.password='"+newpas+"';";

        final  ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        try {
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                user.setPhone(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setRole(resultSet.getString(6));
                user.setActive(resultSet.getBoolean(7));
               user.setBalance(resultSet.getInt(8));
                // user.setPassword(resultSet.getString(9)); //кодирование пароля продумать корректно
                // user.setLogin(resultSet.getString(10));

               // return user;
                System.out.println(user);
            } else {
                System.out.println("problems");
               // logger.info("Login or password is incorrect!");
             //   return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
