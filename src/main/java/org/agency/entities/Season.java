package org.agency.entities;

import org.agency.controllers.HotelController;

import java.time.LocalDate;
import java.util.Date;

public class Season {

    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private int createdBy;
    private int updatedBy;
    private Integer deletedBy; // Use Integer for nullable columns
    private int hotelId;

    // Constructors

    public Season() {
        // Default constructor
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDateLocalDate(LocalDate startDate) {
        this.startDate = java.sql.Date.valueOf(startDate);
    }

    public void setEndDateLocalDate(LocalDate endDate) {
        this.endDate = java.sql.Date.valueOf(endDate);
    }

    public LocalDate getStartDateLocalDate() {
        if (startDate == null) {
            return null;
        }
        return LocalDate.parse(startDate.toString());
    }

    public LocalDate getEndDateLocalDate() {
        if (endDate == null) {
            return null;
        }
        return LocalDate.parse(endDate.toString());
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    // toString method (optional)

    public Hotel getHotel() {
        HotelController hotelController = new HotelController();
        return hotelController.getById(hotelId);
    }

    public void setHotel(Hotel hotel) {
        this.hotelId = hotel.getId();
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", deletedBy=" + deletedBy +
                ", hotelId=" + hotelId +
                '}';
    }
}
