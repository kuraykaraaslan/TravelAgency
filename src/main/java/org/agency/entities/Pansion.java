package org.agency.entities;

import java.util.Date;

public class Pansion {

    private int id;
    private String name; // BED_
    private boolean breakfast;
    private boolean lunch;
    private boolean dinner;
    private boolean midnightSnack;
    private boolean softDrinks;
    private boolean alcoholicDrinks;
    private boolean snacks;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private int createdBy;
    private int updatedBy;
    private Integer deletedBy; // Use Integer for nullable columns
    private int hotelId;

    // Constructors

    public Pansion() {
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

    public boolean isBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

    public boolean isLunch() {
        return lunch;
    }

    public void setLunch(boolean lunch) {
        this.lunch = lunch;
    }

    public boolean isDinner() {
        return dinner;
    }

    public void setDinner(boolean dinner) {
        this.dinner = dinner;
    }

    public boolean isMidnightSnack() {
        return midnightSnack;
    }

    public void setMidnightSnack(boolean midnightSnack) {
        this.midnightSnack = midnightSnack;
    }

    public boolean isSoftDrinks() {
        return softDrinks;
    }

    public void setSoftDrinks(boolean softDrinks) {
        this.softDrinks = softDrinks;
    }

    public boolean isAlcoholicDrinks() {
        return alcoholicDrinks;
    }

    public void setAlcoholicDrinks(boolean alcoholicDrinks) {
        this.alcoholicDrinks = alcoholicDrinks;
    }

    public boolean isSnacks() {
        return snacks;
    }

    public void setSnacks(boolean snacks) {
        this.snacks = snacks;
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

    @Override
    public String toString() {
        return "Pansion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                ", midnightSnack=" + midnightSnack +
                ", softDrinks=" + softDrinks +
                ", alcoholicDrinks=" + alcoholicDrinks +
                ", snacks=" + snacks +
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
