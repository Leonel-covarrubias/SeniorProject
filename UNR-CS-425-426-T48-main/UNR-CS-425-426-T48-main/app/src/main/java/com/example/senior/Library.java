package com.example.senior;
import android.util.Log;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
public class Library {
    private int id;
    private String location;
    private String description;
    private int building;

    public Library(int id, String location, String description, int building) {
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

    // Method to return a string with all the library information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Description: " + description +
                ", Building: " + building;
    }
    public static Library[] createLibrariesFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Library[] libraries = new Library[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                String description = jsonObject.getString("description");
                int building = jsonObject.getInt("building");

                libraries[i] = new Library(id, location, description, building);

                // Log the created Library object
                Log.d("LibraryCreated", "Created: " + libraries[i].getInfo());
            }

            return libraries;
        } catch (Exception e) {
            Log.e("Library", "Error parsing JSON", e);
            return null;
        }
    }
}

