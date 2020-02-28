package by.epam.web.unit6.service;

import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.dto.UserTarif;

import java.util.List;

public interface TarifService {

    List<Tarif>showAllTarif() throws ServiceException;
    Tarif showTarifById(int id) throws ServiceException;
    boolean addTarif(Tarif tarif)throws ServiceException;
    boolean deleteTarif(int id) throws ServiceException;
    boolean changeTarif(Tarif tarif) throws ServiceException;
    List<UserTarif> showTarifsByUserId(int id) throws ServiceException;
    List<Tarif> showTariffRange(int page) throws ServiceException;

}
