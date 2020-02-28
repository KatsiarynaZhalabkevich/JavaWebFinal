package by.epam.web.unit6.dao;

import by.epam.web.unit6.dao.impl.SQLNoteDAO;
import by.epam.web.unit6.dao.impl.SQLTarifDAO;
import by.epam.web.unit6.dao.impl.SQLUserDAO;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDao = new SQLUserDAO();
    private final TarifDAO tarifDAO = new SQLTarifDAO();
    private final NoteDAO noteDAO = new SQLNoteDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDao() {
        return userDao;
    }

    public TarifDAO getTarifDAO() {return tarifDAO;}

    public NoteDAO getNoteDAO(){return noteDAO;}



}
