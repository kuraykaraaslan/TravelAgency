package org.agency.entities;

import org.agency.controllers.HotelController;
import org.agency.controllers.ReservationController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Room {

    private int id;
    private String roomNumber;
    private String type;
    private int doubleBedCount;
    private int singleBedCount;
    private double adultPrice;
    private double childPrice;
    private int squareMeters;
    private boolean hasTelevision;
    private boolean hasBalcony;
    private boolean hasAirConditioning;
    private boolean hasMinibar;
    private boolean hasValuablesSafe;
    private boolean hasGamingConsole;
    private boolean hasProjector;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private int createdBy;
    private int updatedBy;
    private Integer deletedBy; // Use Integer for nullable columns
    private int hotelId;
    private int seasonId;

    private int pansionId;


    // Constructors

    public Room() {
        // Default constructor
    }

    // Getters and Setters

    public Hotel getHotel() {
        HotelController hotelController = new HotelController();
        return hotelController.getById(this.hotelId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = String.valueOf(roomNumber);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDoubleBedCount() {
        return doubleBedCount;
    }

    public void setDoubleBedCount(int doubleBedCount) {
        this.doubleBedCount = doubleBedCount;
    }

    public int getSingleBedCount() {
        return singleBedCount;
    }

    public void setSingleBedCount(int singleBedCount) {
        this.singleBedCount = singleBedCount;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public boolean isHasTelevision() {
        return hasTelevision;
    }

    public void setHasTelevision(boolean hasTelevision) {
        this.hasTelevision = hasTelevision;
    }

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public boolean isHasAirConditioning() {
        return hasAirConditioning;
    }

    public void setHasAirConditioning(boolean hasAirConditioning) {
        this.hasAirConditioning = hasAirConditioning;
    }

    public boolean isHasMinibar() {
        return hasMinibar;
    }

    public void setHasMinibar(boolean hasMinibar) {
        this.hasMinibar = hasMinibar;
    }

    public boolean isHasValuablesSafe() {
        return hasValuablesSafe;
    }

    public void setHasValuablesSafe(boolean hasValuablesSafe) {
        this.hasValuablesSafe = hasValuablesSafe;
    }

    public boolean isHasGamingConsole() {
        return hasGamingConsole;
    }

    public void setHasGamingConsole(boolean hasGamingConsole) {
        this.hasGamingConsole = hasGamingConsole;
    }

    public boolean isHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
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

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getPansionId() {
        return pansionId;
    }

    public void setPansionId(int pansionId) {
        this.pansionId = pansionId;
    }

    public Boolean isRoomAvailableAtDates(LocalDate startDate, LocalDate endDate) {
        //GET ALL RESERVATIONS FOR THIS ROOM
        ReservationController reservationController = new ReservationController();
        ArrayList<Reservation> reservations = reservationController.getByRoomId(this.id);

        //CHECK IF ROOM IS AVAILABLE
        for (Reservation reservation : reservations) {
            if (reservation.getCheckInLocalDate().isBefore(endDate) && reservation.getCheckOutLocalDate().isAfter(startDate)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", type='" + type + '\'' +
                ", doubleBedCount=" + doubleBedCount +
                ", singleBedCount=" + singleBedCount +
                ", adultPrice=" + adultPrice +
                ", childPrice=" + childPrice +
                ", squareMeters=" + squareMeters +
                ", hasTelevision=" + hasTelevision +
                ", hasBalcony=" + hasBalcony +
                ", hasAirConditioning=" + hasAirConditioning +
                ", hasMinibar=" + hasMinibar +
                ", hasValuablesSafe=" + hasValuablesSafe +
                ", hasGamingConsole=" + hasGamingConsole +
                ", hasProjector=" + hasProjector +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", deletedBy=" + deletedBy +
                ", hotelId=" + hotelId +
                ", seasonId=" + seasonId +
                ", pansionId=" + pansionId +
                '}';
    }
}
