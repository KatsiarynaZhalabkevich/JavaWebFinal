package by.epam.web.unit6.dao.impl;

import by.epam.web.unit6.bean.Note;
import by.epam.web.unit6.dao.DAOException;
import by.epam.web.unit6.dao.NoteDAO;
import by.epam.web.unit6.pool.ConnectionPool;
import by.epam.web.unit6.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLNoteDAO implements NoteDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String ADD_NOTE="INSERT INTO tarif_note (id, user_id, tarif_id) values (?,?,?)";
    private final static String DELETE_BY_ID = "DELETE FROM tarif_note WHERE id=?";
    private final static String TAKE_BY_USER_ID="SELECT * FROM tarif_note WHERE user_id=?";
    private final static String TAKE_BY_TARIF_ID="SELECT * FROM tarif_note WHERE tarif_id=?";


    private Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLNoteDAO(){
        try (Connection connection = connectionPool.takeConnection()) {
            setPreparedStatement(connection, ADD_NOTE);
            setPreparedStatement(connection, DELETE_BY_ID);
            setPreparedStatement(connection,TAKE_BY_USER_ID);
            setPreparedStatement(connection, TAKE_BY_TARIF_ID);


        } catch (ConnectionPoolException | SQLException | DAOException e) {
            logger.error(e);

        }
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
    public boolean addNote(Note note) throws DAOException {
        boolean flag = false;

        try  {
            PreparedStatement statement = preparedStatementMap.get(ADD_NOTE);

            statement.setInt(1, note.getId());
            statement.setInt(2, note.getUserId());
            statement.setInt(3, note.getTarifId());
            if (statement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return flag;
    }

    @Override
    public boolean deleteNoteById(int id) throws DAOException {

        boolean flag = false;

        try  {
            PreparedStatement statement = preparedStatementMap.get(DELETE_BY_ID);
            statement.setInt(1, id);

            if (statement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return flag;
    }

    @Override
    public List<Note> takeNoteByUserId(int id) throws DAOException {
        List<Note> notes = new ArrayList<>();

        try {
            PreparedStatement statement = preparedStatementMap.get(TAKE_BY_USER_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
               note.setId( resultSet.getInt("id"));
               note.setUserId(resultSet.getInt("user_id"));
               note.setTarifId(resultSet.getInt("tarif_id"));
               note.setCreateNote(resultSet.getDate("create_time"));
               notes.add(note);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return notes;
    }

    @Override
    public List<Note> takeNoteByTarifId(int id) throws DAOException {
        List<Note> notes = new ArrayList<>();

        try  {
            PreparedStatement statement = preparedStatementMap.get(TAKE_BY_TARIF_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId( resultSet.getInt("id"));
                note.setUserId(resultSet.getInt("user_id"));
                note.setTarifId(resultSet.getInt("tarif_id"));
                note.setCreateNote(resultSet.getDate("create_time"));
                notes.add(note);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return notes;
    }


}
