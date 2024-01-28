package org.agency.controllers;

import java.util.HashMap;
import java.util.List;

import org.agency.core.PaginatedResult;
import org.agency.dao.ReservationDao;
import org.agency.entities.Reservation;
import org.agency.entities.Room;
import org.agency.entities.User;

public class ReservationController {

    private ReservationDao reservationDao;

    public ReservationController() {
        reservationDao = new ReservationDao();
    }

    public void create(Reservation reservation) {
        reservationDao.insert(reservation);
        System.out.println("Reservation created successfully.");
    }

    public Reservation getById(int reservationId) {
        return reservationDao.getById(reservationId);
    }

    public List<Reservation> getAll() {
        return reservationDao.getAll();
    }

    public void update(Reservation reservation) {
        reservationDao.update(reservation);
        System.out.println("Reservation updated successfully.");
    }

    public void delete(Reservation reservation) {
        reservationDao.delete(reservation);
        System.out.println("Reservation deleted successfully.");
    }

    //getByFilters

    public List<Reservation> getByFilters(HashMap<String, Object> filters) {
        return reservationDao.getByFilters(filters);
    }

    //GET BY HOTEL ID
    public List<Reservation> getByHotelId(int hotelId) {
        return reservationDao.getByHotelId(hotelId);
    }

    public PaginatedResult<Reservation> paginate(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = offset + pageSize;
        return reservationDao.paginate(offset, limit);
    }

    public PaginatedResult<Reservation> paginate(int page, int pageSize, String query) {
        int offset = (page - 1) * pageSize;
        int limit = offset + pageSize;
        return reservationDao.paginate(offset, limit, query);
    }


}
