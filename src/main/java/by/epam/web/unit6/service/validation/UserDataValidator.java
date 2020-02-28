package by.epam.web.unit6.service.validation;

import by.epam.web.unit6.bean.User;

public class UserDataValidator {
    private static final UserDataValidator instance = new UserDataValidator();

    /**
     * Класс для проверки данных пользователя при регистрации
     */
    private UserDataValidator() {
    }

    public static UserDataValidator getInstance() {
        return instance;
    }

    //проверить на валидность логин и пароль
    //логин должен быть уникальным? не пустой строкой и макс длина меньше

    public boolean checkLogin(String login) { //у админа данные не правильные
        boolean flag = true; //пока только положительный сценарий
        String regLog = "[0-9a-zA-Z]{4,10}";
   /*     if (login != null && !login.equals("")) {
            flag = login.matches(regLog);
        }*/
        return flag;
    }

    public boolean checkPassword(String password) {
        boolean flag = true; //пока рассматриваем положительный сценарий
        String regPas = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{6,}";
   /*   if (password != null) {
            if (!password.equals("")) {
                flag = password.matches(regPas);
            }
        }*/
        return flag;
    }

    public boolean checkName(String name) {
        boolean flag = false;
        String regExp = "^[а-яА-Я]{30}|[a-zA-Z]{30}$";
      if(name!=null){
            if (!name.equals("")){

                    flag = true;

            }
        }

        return flag;
    }

    public boolean checkPhone(String phone) {
        String regExp = "^\\+?(\\d{1,3})?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$";

        return phone != null && phone.matches(regExp);
    }

    public boolean checkEmail(String email) {
        String regExp = "^[A-Z0-9_]+@[A-Z0-9-]+.+.[A-Z]{2,4}$";

       // return email != null && email.matches(regExp);
        return true;

    }

    public boolean userValidate(User user){
        boolean name = checkName(user.getName());
        boolean surname =checkName(user.getSurname());
        boolean login = checkLogin(user.getLogin());
        boolean password = checkPassword(user.getPassword());
        boolean email = checkEmail(user.getEmail());
        boolean phone = checkPhone(user.getPhone());

        return (name&&surname&&login&&password&&email&&phone);
    }


}
