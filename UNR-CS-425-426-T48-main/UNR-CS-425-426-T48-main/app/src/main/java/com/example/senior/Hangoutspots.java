package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Hangoutspots {
    private int id;
    private String location;
    private String description;
    private boolean outlet;
    private int building;

    public Hangoutspots(int id, String location, String description, boolean outlet, int building) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.outlet = outlet;
        this.building = building;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasOutlet() {
        return outlet;
    }

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the hangout spot information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Description: " + description +
                ", Outlet: " + (outlet ? "Yes" : "No") +
                ", Building: " + building;
    }

    public static Hangoutspots[] createHangoutSpotsFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Hangoutspots[] hangoutSpots = new Hangoutspots[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                String description = jsonObject.getString("description");
                boolean outlet = jsonObject.getBoolean("outlet");
                int building = jsonObject.getInt("building");

                hangoutSpots[i] = new Hangoutspots(id, location, description, outlet, building);

                // Log the created HangoutSpot object
                Log.d("HangoutSpotCreated", "Created: " + hangoutSpots[i].getInfo());
            }

            return hangoutSpots;
        } catch (Exception e) {
            Log.e("HangoutSpot", "Error parsing JSON", e);
            return null;
        }
    }
}
