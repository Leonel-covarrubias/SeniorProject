package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class SpecialFeature {
    private int id;
    private String location;
    private String description;
    private int building;

    public SpecialFeature(int id, String location, String description, int building) {
        this.id = id;
        this.location = location;
        this.description = description;
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

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the special feature information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Description: " + description +
                ", Building: " + building;
    }

    public static SpecialFeature[] createSpecialFeaturesFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            SpecialFeature[] specialFeatures = new SpecialFeature[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                String description = jsonObject.getString("description");
                int building = jsonObject.getInt("building");

                specialFeatures[i] = new SpecialFeature(id, location, description, building);

                // Log the created SpecialFeature object
                Log.d("SpecialFeatureCreated", "Created: " + specialFeatures[i].getInfo());
            }

            return specialFeatures;
        } catch (Exception e) {
            Log.e("SpecialFeature", "Error parsing JSON", e);
            return null;
        }
    }
}
