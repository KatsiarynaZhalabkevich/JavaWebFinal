package by.epam.web.unit6.dao;

import by.epam.web.unit6.bean.Note;

import java.util.List;

public interface NoteDAO {
    boolean addNote(Note note) throws DAOException;
    boolean deleteNoteById(int id) throws DAOException;
    List<Note> takeNoteByUserId(int id) throws  DAOException;
    List<Note> takeNoteByTarifId(int id) throws DAOException;

}
