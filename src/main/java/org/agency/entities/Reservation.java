package org.agency.entities;

import org.agency.controllers.HotelController;
import org.agency.controllers.PansionController;
import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {

    private int id;
    private String guestCitizenId;
    private String guestFullName;
    private String guestEmail;
    private String guestPhone;
    private Date checkIn;
    private Date checkOut;

    private String status;
    private int adultCount;
    private int childCount;
    private double price;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private int createdBy;
    private int updatedBy;
    private Integer deletedBy; // Use Integer for nullable columns
    private int hotelId;
    private int roomId;

    private int pansionId;

    private int seasonId;

    // Constructors

    public Reservation() {
        // Default constructor
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuestCitizenId() {
        return guestCitizenId;
    }

    public void setGuestCitizenId(String guestCitizenId) {
        this.guestCitizenId = guestCitizenId;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public void setGuestFullName(String guestFullName) {
        this.guestFullName = guestFullName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckInLocalDate() {
        if (checkIn == null) {
            return null;
        }
        return LocalDate.parse(checkIn.toString());
    }

    public LocalDate getCheckOutLocalDate() {
        if (checkOut == null) {
            return null;
        }
        return LocalDate.parse(checkOut.toString());
    }

    public void setCheckInLocalDate(LocalDate checkIn) {
        this.checkIn = java.sql.Date.valueOf(checkIn);
    }

    public void setCheckOutLocalDate(LocalDate checkOut) {
        this.checkOut = java.sql.Date.valueOf(checkOut);
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        if (status != null) {
            return status;
        } else {
            if (checkIn != null && checkOut != null) {
                LocalDate today = LocalDate.now();
                LocalDate checkInLocalDate = LocalDate.parse(checkIn.toString());
                LocalDate checkOutLocalDate = LocalDate.parse(checkOut.toString());
                if (today.isBefore(checkInLocalDate)) {
                    return "PENDING";
                } else if (today.isAfter(checkInLocalDate) && today.isBefore(checkOutLocalDate)) {
                    return "CHECKED_IN";
                } else if (today.isAfter(checkOutLocalDate)) {
                    return "CHECKED_OUT";
                }
            }
        }
        return "PENDING";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method (optional)

    // setPansionId
    public void setPansionId(int pansionId) {
        this.pansionId = pansionId;
    }

    public int getPansionId() {
        return pansionId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public Season getSeason() {
        SeasonController seasonController = new SeasonController();
        return seasonController.getById(seasonId);
    }

    public Pansion getPansion() {
        PansionController pansionController = new PansionController();
        return pansionController.getById(pansionId);
    }

    public Room getRoom() {
        RoomController roomController = new RoomController();
        return roomController.getById(roomId);
    }

    public Hotel getHotel() {
        HotelController hotelController = new HotelController();
        return hotelController.getById(hotelId);
    }

    public void setHotel(Hotel hotel) {
        this.hotelId = hotel.getId();
    }

    public void setRoom(Room room) {
        this.roomId = room.getId();
    }

    public void setSeason(Season season) {
        this.seasonId = season.getId();
    }

    public void setPansion(Pansion pansion) {
        this.pansionId = pansion.getId();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", guestCitizenId='" + guestCitizenId + '\'' +
                ", guestFullName='" + guestFullName + '\'' +
                ", guestEmail='" + guestEmail + '\'' +
                ", guestPhone='" + guestPhone + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", adultCount=" + adultCount +
                ", childCount=" + childCount +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", deletedBy=" + deletedBy +
                ", hotelId=" + hotelId +
                ", roomId=" + roomId +
                ", pansionId=" + pansionId +
                ", seasonId=" + seasonId +
                ", status='" + status + '\'' +
                ", season=" + getSeason() +
                ", pansion=" + getPansion() +
                ", room=" + getRoom() +
                '}';
    }
}
