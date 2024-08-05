package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class Lab {
    private int id;
    private String location;
    private String type;
    private String department;
    private int building;

    public Lab(int id, String location, String type, String department, int building) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.department = department;
        this.building = building;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getDepartment() {
        return department;
    }

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the lab information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Type: " + type +
                ", Department: " + department +
                ", Building: " + building;
    }

    public static Lab[] createLabsFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Lab[] labs = new Lab[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                String type = jsonObject.getString("type");
                String department = jsonObject.getString("department");
                int building = jsonObject.getInt("building");

                labs[i] = new Lab(id, location, type, department, building);

                // Log the created Lab object
                Log.d("LabCreated", "Created: " + labs[i].getInfo());
            }

            return labs;
        } catch (Exception e) {
            Log.e("Lab", "Error parsing JSON", e);
            return null;
        }
    }
}
