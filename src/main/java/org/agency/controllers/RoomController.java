package org.agency.controllers;

import org.agency.core.PaginatedResult;
import org.agency.dao.RoomDao;
import org.agency.entities.Room;
import org.agency.entities.Season;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomController {

    private RoomDao roomDao;

    public RoomController() {
        roomDao = new RoomDao();
    }

    public Room getById(int roomId) {
        return roomDao.getById(roomId);
    }

    public ArrayList<Room> getAll() {
        return roomDao.getAll();
    }

    public void update(Room room) {
        roomDao.update(room);
        System.out.println("Room updated successfully.");
    }

    public void create(Room room) {
        //System.out.println("ROOM CONTROLLER: " + room);
        roomDao.insert(room);
    }

    public void delete(Room room) {
        roomDao.delete(room);
        System.out.println("Room deleted successfully.");
    }

    public ArrayList<Room> getByHotelId(int hotelId) {
        return roomDao.getByHotelId(hotelId);
    }

    public ArrayList<Room> getByHotelId(int hotelId, String query) {
        return roomDao.getByHotelId(hotelId, query);
    }

    //getByHotelAndSeasonId
    public ArrayList<Room> getByHotelAndSeasonId(int hotelId, int seasonId) {
        return roomDao.getByHotelAndSeasonId(hotelId, seasonId);
    }

    //isRoomAvailable
    public boolean isRoomAvailable(int roomId, LocalDate startDate, LocalDate endDate) {

        //check for other reservations
        org.agency.controllers.ReservationController reservationController = new org.agency.controllers.ReservationController();

        //get all reservations for this room
        ArrayList<org.agency.entities.Reservation> reservations = reservationController.getByRoomId(roomId);

        //check if there are any reservations
        if (reservations.size() > 0) {
            for (org.agency.entities.Reservation reservation : reservations) {

                LocalDate[] reservationDates = new LocalDate[2];
                reservationDates[0] = reservation.getCheckInLocalDate();
                reservationDates[1] = reservation.getCheckOutLocalDate();

                LocalDate[] newReservationDates = new LocalDate[2];
                newReservationDates[0] = startDate;
                newReservationDates[1] = endDate;

                //check if there is any reservation that overlaps with the new reservation
                for (LocalDate date1 : reservationDates) {
                    for (LocalDate date2 : newReservationDates) {
                        if (!date1.isAfter(date2) && !date2.isAfter(date1)) {
                            // Overlapping date found
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isDatesValid(int roomId, LocalDate startDate, LocalDate endDate) {
        //Get Room by id
        Room room = getById(roomId);

        //get season id
        SeasonController seasonController = new SeasonController();
        Season season = seasonController.getById(room.getSeasonId());

        //COMPARE DATES WITH SEASON DATES
        if (startDate.isBefore(season.getStartDateLocalDate()) || endDate.isAfter(season.getEndDateLocalDate())) {
            return false;

        }
        return true;
    }

    public ArrayList<Room> getByHotelAndSeasonIdAndPansionId(int hotelId, int seasonId, int pansionId) {
        return roomDao.getByHotelAndSeasonIdAndPansionId(hotelId, seasonId, pansionId);
    }


    public ArrayList<Room> getByFilters(HashMap<String, Object> filters) {
        return roomDao.getByFilters(filters);
    }

    public ArrayList<Room> getAllHotelJoinedByFilters(HashMap<String, Object> filters) {
        return roomDao.getAllHotelJoinedByFilters(filters);
    }
}