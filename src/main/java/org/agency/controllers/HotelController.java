package org.agency.controllers;

import java.util.ArrayList;
import java.util.List;

import org.agency.core.PaginatedResult;
import org.agency.dao.HotelDao;
import org.agency.entities.Hotel;
import org.agency.entities.User;

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

    public ArrayList<Hotel> getAll() {
        return hotelDao.getAll();
    }

    public void update(Hotel hotel) {
        hotelDao.update(hotel);
        System.out.println("Hotel updated successfully.");
    }

    public void delete(Hotel hotel) {
        hotelDao.delete(hotel);
        System.out.println("Hotel deleted successfully.");
    }

    public PaginatedResult<Hotel> paginate(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = offset + pageSize;
        return hotelDao.paginate(offset, limit);
    }

    public PaginatedResult<Hotel> paginate(int page, int pageSize, String query) {
        int offset = (page - 1) * pageSize;
        int limit = offset + pageSize;
        return hotelDao.paginate(offset, limit, query);
    }
}
