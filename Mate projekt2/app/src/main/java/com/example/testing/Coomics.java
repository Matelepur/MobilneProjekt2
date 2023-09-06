package com.example.testing;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comics")
public class Coomics {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String comicName;
    private String publishingHouse;
    private String ageDemographic;
    private String imagePath;

    // Constructor, getters, and setters should be defined here
    // ...


    // Constructor
    public Coomics(String comicName, String publishingHouse, String ageDemographic, String imagePath) {
        this.comicName = comicName;
        this.publishingHouse = publishingHouse;
        this.ageDemographic = ageDemographic;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getAgeDemographic() {
        return ageDemographic;
    }

    public void setAgeDemographic(String ageDemographic) {
        this.ageDemographic = ageDemographic;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
