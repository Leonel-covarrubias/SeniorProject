package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Resource {
    private int id;
    private String description;
    private String location;
    private String operatingHours;
    private String weblink;
    private int building;

    public Resource(int id, String description, String location, String operatingHours, String weblink, int building) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.operatingHours = operatingHours;
        this.weblink = weblink;
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

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the resource information
    public String getInfo() {
        return "ID: " + id +
                ", Description: " + description +
                ", Location: " + location +
                ", Operating Hours: " + operatingHours +
                ", Web Link: " + weblink +
                ", Building: " + building;
    }

    public static Resource[] createResourcesFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Resource[] resources = new Resource[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String description = jsonObject.getString("description");
                String location = jsonObject.getString("location");
                String operatingHours = jsonObject.getString("operating_hours");
                String weblink = jsonObject.getString("weblink");
                int building = jsonObject.getInt("building");

                resources[i] = new Resource(id, description, location, operatingHours, weblink, building);

                // Log the created Resource object
                Log.d("ResourceCreated", "Created: " + resources[i].getInfo());
            }

            return resources;
        } catch (Exception e) {
            Log.e("Resource", "Error parsing JSON", e);
            return null;
        }
    }
}
