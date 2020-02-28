package by.epam.web.unit6.pool;

import java.util.ResourceBundle;

public class DBResourсeManager {

    private  final static DBResourсeManager instance = new DBResourсeManager();
    private ResourceBundle bundle = ResourceBundle.getBundle("db");
    public static DBResourсeManager getInstance(){
        return instance;
    }
    public String getValue (String key){
        return bundle.getString(key);
    }
}
