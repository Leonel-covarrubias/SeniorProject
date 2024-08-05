package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class VendingMachine {
    private int id;
    private String location;
    private String type;
    private int building;

    public VendingMachine(int id, String location, String type, int building) {
        this.id = id;
        this.location = location;
        this.type = type;
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

    public int getBuilding() {
        return building;
    }

    // Method to return a string with all the vending machine information
    public String getInfo() {
        return "ID: " + id +
                ", Location: " + location +
                ", Type: " + type +
                ", Building: " + building;
    }

    public static VendingMachine[] createVendingMachinesFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            VendingMachine[] vendingMachines = new VendingMachine[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String location = jsonObject.getString("location");
                String type = jsonObject.getString("type");
                int building = jsonObject.getInt("building");

                vendingMachines[i] = new VendingMachine(id, location, type, building);

                // Log the created VendingMachine object
                Log.d("VendingMachineCreated", "Created: " + vendingMachines[i].getInfo());
            }

            return vendingMachines;
        } catch (Exception e) {
            Log.e("VendingMachine", "Error parsing JSON", e);
            return null;
        }
    }
}
