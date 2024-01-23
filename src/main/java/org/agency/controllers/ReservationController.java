package org.agency.controllers;

import java.util.List;
import org.agency.dao.ReservationDao;
import org.agency.entities.Reservation;

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

}
