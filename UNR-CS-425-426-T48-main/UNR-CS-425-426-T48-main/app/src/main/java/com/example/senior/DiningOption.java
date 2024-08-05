package com.example.senior;

import android.util.Log;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

public class DiningOption {
    private int id;
    private String description;
    private String location;
    private String operatingHours;
    private String weblink;
    private String menulink;
    private int building;

    public DiningOption(int id, String description, String location, String operatingHours, String weblink, String menulink, int building) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.operatingHours = operatingHours;
        this.weblink = weblink;
        this.menulink = menulink;
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

    public String getWeblink() {
        return weblink;
    }

    public String getMenulink() {
        return menulink;
    }

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the dining option information
    public String getInfo() {
        return "ID: " + id +
                ", Description: " + description +
                ", Location: " + location +
                ", Operating Hours: " + operatingHours +
                ", Web Link: " + weblink +
                ", Menu Link: " + menulink +
                ", Building: " + building;
    }
    public static DiningOption[] createDiningOptionsFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            DiningOption[] diningOptions = new DiningOption[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String description = jsonObject.getString("description");
                String location = jsonObject.getString("location");
                String operatingHours = jsonObject.getString("operating_hours");
                String weblink = jsonObject.getString("weblink");
                String menulink = jsonObject.getString("menulink");
                int building = jsonObject.getInt("building");

                diningOptions[i] = new DiningOption(id, description, location, operatingHours, weblink, menulink, building);

                // Log the created DiningOption object
                Log.d("DiningOptionCreated", "Created: " + diningOptions[i].getInfo());
            }

            return diningOptions;
        } catch (Exception e) {
            Log.e("DiningOption", "Error parsing JSON", e);
            return null;
        }
    }
}

