package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Store {
    private int id;
    private String description;
    private String location;
    private String operatingHours;
    private int building;

    public Store(int id, String description, String location, String operatingHours, int building) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.operatingHours = operatingHours;
        this.building = building;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the store information
    public String getInfo() {
        return "ID: " + id +
                ", Description: " + description +
                ", Location: " + location +
                ", Operating Hours: " + operatingHours +
                ", Building: " + building;
    }

    public static Store[] createStoreFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Store[] stores = new Store[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String description = jsonObject.getString("description");
                String location = jsonObject.getString("location");
                String operatingHours = jsonObject.getString("operating_hours");
                int building = jsonObject.getInt("building");

                stores[i] = new Store(id, description, location, operatingHours, building);

                // Log the created Store object
                Log.d("StoreCreated", "Created: " + stores[i].getInfo());
            }

            return stores;
        } catch (Exception e) {
            Log.e("Store", "Error parsing JSON", e);
            return null;
        }
    }
}
