package com.example.senior;

public class UserNotes {
    private String name;
    private int roomNumber;
    private String buildingName;


    public UserNotes(String name, int roomNumber, String buildingName) {
        this.name = name;
        this.roomNumber = roomNumber;
        this.buildingName = buildingName;
    }


    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    // Outputting the object info
    @Override
    public String toString() {
        return name + "\n" + roomNumber + "\n" + buildingName;
    }
}
