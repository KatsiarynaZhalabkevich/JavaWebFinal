package by.epam.web.unit6.service.impl;

import by.epam.web.unit6.bean.Note;
import by.epam.web.unit6.dao.DAOException;
import by.epam.web.unit6.dao.DAOProvider;
import by.epam.web.unit6.dao.NoteDAO;
import by.epam.web.unit6.service.NoteService;
import by.epam.web.unit6.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NoteServiceImpl implements NoteService {
    private final static Logger logger = LogManager.getLogger();
    private final static NoteDAO noteDAO = DAOProvider.getInstance().getNoteDAO();
    @Override
    public boolean addNote(Note note) throws ServiceException {
        boolean flag;

        try {
            flag = noteDAO.addNote(note);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean deleteNote(int id) throws ServiceException {
        boolean flag;
        try {
            flag=noteDAO.deleteNoteById(id);
        } catch (DAOException e) {
           throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public List<Note> findNoteByTarifId(int id) throws ServiceException {
        List<Note> notes = new ArrayList<>();
        try {
           notes.addAll( noteDAO.takeNoteByTarifId(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return notes;
    }

    @Override
    public List<Note> findNoteByUserId(int id) throws ServiceException {
        List<Note> notes = new ArrayList<>();
        try {
            notes.addAll( noteDAO.takeNoteByUserId(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return notes;
    }
}
