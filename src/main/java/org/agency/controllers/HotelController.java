package org.agency.controllers;

import java.util.List;
import org.agency.dao.HotelDao;
import org.agency.entities.Hotel;

public class HotelController {

    private HotelDao hotelDao;

    public HotelController() {
        hotelDao = new HotelDao();
    }

    public void create(Hotel hotel) {
        hotelDao.insert(hotel);
        System.out.println("Hotel created successfully.");
    }

    public Hotel getById(int hotelId) {
        return hotelDao.getById(hotelId);
    }

    public List<Hotel> getAll() {
        return hotelDao.getAll();
    }
}
