package org.agency.controllers;

import java.util.ArrayList;
import java.util.List;
import org.agency.dao.PansionDao;
import org.agency.entities.Pansion;

public class PansionController {

    private PansionDao pansionDao;

    public PansionController() {
        pansionDao = new PansionDao();
    }

    public void create(Pansion pansion) {
        pansionDao.insert(pansion);
        System.out.println("Pansion created successfully.");
    }

    public Pansion getById(int pansionId) {
        return pansionDao.getById(pansionId);
    }

    public ArrayList<Pansion> getAll() {
        return pansionDao.getAll();
    }

    public void update(Pansion pansion) {
        pansionDao.update(pansion);
        System.out.println("Pansion updated successfully.");
    }

    public void delete(Pansion pansion) {
        pansionDao.delete(pansion);
        System.out.println("Pansion deleted successfully.");
    }

    public ArrayList<Pansion> getByHotelId(int hotelId) {
        return pansionDao.getByHotelId(hotelId);
    }


}
