package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Elevator {
    private int id;
    private String location;
    private int building;

    public Elevator(int id, String location, int building) {
        this.id = id;
        this.location = location;
        this.building = building;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the elevator information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Building: " + building;
    }

    public static Elevator[] createElevatorsFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Elevator[] elevators = new Elevator[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                int building = jsonObject.getInt("building");

                elevators[i] = new Elevator(id, location, building);

                // Log the created Elevator object
                Log.d("ElevatorCreated", "Created: " + elevators[i].getInfo());
            }

            return elevators;
        } catch (Exception e) {
            Log.e("Elevator", "Error parsing JSON", e);
            return null;
        }
    }
}
