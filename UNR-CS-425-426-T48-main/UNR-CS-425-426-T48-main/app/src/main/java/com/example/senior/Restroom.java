package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Restroom {
    private int id;
    private String location;
    private boolean accessibility;
    private int building;

    public Restroom(int id, String location, boolean accessibility, int building) {
        this.id = id;
        this.location = location;
        this.accessibility = accessibility;
        this.building = building;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the restroom information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Accessibility: " + (accessibility ? "Yes" : "No") +
                ", Building: " + building;
    }

    public static Restroom[] createRestroomsFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Restroom[] restrooms = new Restroom[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                boolean accessibility = jsonObject.getBoolean("accessibility");
                int building = jsonObject.getInt("building");

                restrooms[i] = new Restroom(id, location, accessibility, building);

                // Log the created Restroom object
                Log.d("RestroomCreated", "Created: " + restrooms[i].getInfo());
            }

            return restrooms;
        } catch (Exception e) {
            Log.e("Restroom", "Error parsing JSON", e);
            return null;
        }
    }
}
