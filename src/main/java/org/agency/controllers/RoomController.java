package org.agency.controllers;

import org.agency.core.PaginatedResult;
import org.agency.dao.RoomDao;
import org.agency.entities.Room;

import java.util.HashMap;
import java.util.List;

public class RoomController {

    private RoomDao  roomDao;

    public RoomController() {
        roomDao = new RoomDao();
    }

    public Room getById(int roomId) {
        return roomDao.getById(roomId);
    }

    public List<Room> getAll() {
        return roomDao.getAll();
    }

    public void update(Room room) {
        roomDao.update(room);
        System.out.println("Room updated successfully.");
    }

    public void delete(Room room) {
        roomDao.delete(room);
        System.out.println("Room deleted successfully.");
    }

    public List<Room> getByHotelId(int hotelId) {
        return roomDao.getByHotelId(hotelId);
    }

    public List<Room> getByHotelId(int hotelId, String query) {
        return roomDao.getByHotelId(hotelId, query);
    }

    //getByFilters

    public List<Room> getByFilters(HashMap<String, Object> filters) {
        return roomDao.getByFilters(filters);
    }
}

