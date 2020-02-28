package by.epam.web.unit6.service;

import by.epam.web.unit6.bean.Tarif;
import by.epam.web.unit6.bean.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TarifServiceTest {
    private final static Logger logger = LogManager.getLogger();
    TarifService tarifService = ServiceProvider.getInstance().getTarifService();
    private static Tarif tarif = new Tarif();

    @BeforeClass
    public static void createTariffForTest() {
        String name = "Test";
        double price = 60;
        int speed = 300;
        double discount = 0;
        String description = "Tariff for test run";

        tarif.setName(name);
        tarif.setDescription(description);
        tarif.setSpeed(speed);
        tarif.setDiscount(discount);
        tarif.setPrice(price);
    }

    @Test
    public void addTariffTest() {
        boolean isAdded = false;
        int id;
        Tarif testTarif = tarif;
        try {
            isAdded = tarifService.addTarif(testTarif);
            id = getTariffId(testTarif);
            tarifService.deleteTarif(id);
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertTrue(isAdded);
    }

    @Test
    public void changeTariffTest() {
        boolean isChanged = false;
        int id;
        Tarif testTariff = tarif;
        String name = "New name for test";
        double price = 55;

        try {
            tarifService.addTarif(testTariff);
            id = getTariffId(testTariff);
            testTariff.setPrice(price);
            testTariff.setName(name);
            testTariff.setId(id);
            logger.info(testTariff);
            isChanged = tarifService.changeTarif(testTariff);
            tarifService.deleteTarif(id);
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(isChanged);

    }

    @Test
    public void allTariffsTest() {
        List<Tarif> tariffs = new ArrayList<>();
        try {
            tariffs = tarifService.showAllTarif();
        } catch (ServiceException e) {
            logger.error(e);
        }
        Assert.assertEquals(4, tariffs.size());
    }

    @Test
    public void deleteTariffTest() {
        boolean isDeleted = false;
        int id;
        Tarif testTariff = tarif;

        try {
            tarifService.addTarif(testTariff);
            id = getTariffId(testTariff);
            isDeleted = tarifService.deleteTarif(id);
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(isDeleted);
    }


    private int getTariffId(Tarif tarif) {
        int id = -1;
        List<Tarif> tariffs = new ArrayList<>();
        try {
            tariffs = tarifService.showAllTarif();
        } catch (ServiceException e) {
            logger.error(e);
        }
        for (Tarif t : tariffs) {

            if (t.getName().equals(tarif.getName())) {
                id = t.getId();

            }
        }
        return id;
    }
}
