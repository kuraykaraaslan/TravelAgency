package org.agency.controllers;

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

    public List<Pansion> getAll() {
        return pansionDao.getAll();
    }

}
