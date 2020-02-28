package by.epam.web.unit6.bean.util;


import org.mindrot.jbcrypt.BCrypt;
/**
 *  This class crypt user's password
 */

public class PasswordCreater {
    /**
     *
     * @param password пароль пользователя в исходном виде
     * @return зашифрованный пароль
     */

    public static String createPassword(String password){

       return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    /**
     *
     * @param pass пароль, введенный пользователем для проверки
     * @param hashPass пароль, который хранится в БД
     * @return boolean
     */
    public static boolean verifyPassword(String pass, String hashPass){

        return  BCrypt.checkpw(pass,hashPass);
    }
}
