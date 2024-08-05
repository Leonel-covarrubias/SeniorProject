package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Staircase {
    private int id;
    private String location;
    private int building;

    public Staircase(int id, String location, int building) {
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

    // Method to return a string with all the staircase information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Building: " + building;
    }

    public static Staircase[] createStaircasesFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Staircase[] staircases = new Staircase[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                int building = jsonObject.getInt("building");

                staircases[i] = new Staircase(id, location, building);

                // Log the created Staircase object
                Log.d("StaircaseCreated", "Created: " + staircases[i].getInfo());
            }

            return staircases;
        } catch (Exception e) {
            Log.e("Staircase", "Error parsing JSON", e);
            return null;
        }
    }
}
