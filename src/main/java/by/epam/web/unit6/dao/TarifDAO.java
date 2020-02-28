package by.epam.web.unit6.dao;

import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.dto.UserTarif;

import java.util.List;

public interface TarifDAO {

    boolean addTarif(Tarif tarif) throws DAOException;
    boolean editTarif(Tarif tarif) throws DAOException;
    Tarif getTarifById(int id) throws DAOException;
    List<Tarif> getAll()throws DAOException;
    boolean deleteTarifById(int id) throws DAOException;
    List<UserTarif>getTarifByUserId(int id) throws DAOException;
    List<Tarif> getTariffRange(int firstPosition, int limit) throws DAOException;

}
