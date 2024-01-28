package org.agency.controllers;

import org.agency.dao.SeasonDao;
import org.agency.entities.Season;

import java.util.HashMap;
import java.util.List;

public class SeasonController {

    private SeasonDao seasonDao;

    public SeasonController() {
        seasonDao = new SeasonDao();
    }

    public void create(Season season) {
        seasonDao.insert(season);
        System.out.println("Season created successfully.");
    }

    public Season getById(int seasonId) {
        return seasonDao.getById(seasonId);
    }

    public List<Season> getAll() {
        return seasonDao.getAll();
    }

    public void update(Season season) {
        seasonDao.update(season);
        System.out.println("Season updated successfully.");
    }

    public void delete(Season season) {
        seasonDao.delete(season);
        System.out.println("Season deleted successfully.");
    }

    //getAllByHotelId
    public List<Season> getAllByHotelId(int hotelId) {
        return seasonDao.getAllByHotelId(hotelId);
    }


    public List<Season> getByFilters(HashMap<String, Object> filters) {
        return seasonDao.getByFilters(filters);
    }
}

