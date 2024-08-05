package com.example.senior;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class Building {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String buildingCode;
    private String buildingNum;
    private String department;
    private String operatingHours;
    private Library[] libraries;
    private Hangoutspots[] hangoutspots;
    private Restroom[] restrooms;
    private VendingMachine[] vendingMachines;
    private Resource[] resources;
    private Store[] stores;
    private Lab[] labs;
    private Elevator[] elevators;
    private Staircase[] staircases;
    private SpecialFeature[] specialFeatures;



    public Building(int id, String name, String buildingCode, double latitude, double longitude, String buildingNum, String department, String operatingHours) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.buildingCode = buildingCode;
        this.buildingNum = buildingNum;
        this.department = department;
        this.operatingHours = operatingHours;
        }

    // Getter methods
    public String getInfo() {
        return "ID: " + id +
                ", Name: " + name +
                ", Latitude: " + latitude +
                ", Longitude: " + longitude +
                ", Building Code: " + buildingCode +
                ", Building Number: " + buildingNum +
                ", Department: " + department +
                ", Operating Hours: " + operatingHours;
    }

    public int getId() {return id;}
    public String getName() {return name;}

    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}

    public String getBuildingCode() {return buildingCode;}
    public String getBuildingNum() {return buildingNum;}
    public String getDepartment() {return department;}
    public String getOperatingHours() {return operatingHours;}


    public Building[] getClosestBuildings(double userLat, double userLon ,Building [] buildings) {


        Log.d("User Cords", userLat + " "+ userLon);

        // Sorting the buildings by distance

        Arrays.sort(buildings, (b1, b2) -> {
            double distanceToB1 = calculateDistance(userLat, userLon, b1.getLatitude(), b1.getLongitude());
            double distanceToB2 = calculateDistance(userLat, userLon, b2.getLatitude(), b2.getLongitude());
            return Double.compare(distanceToB1, distanceToB2);
        });

        // get the 4 closest buildings
        Building[] closestBuildings = Arrays.copyOfRange(buildings, 0, Math.min(buildings.length, 4));
        for (Building building : buildings) {
            double distance = calculateDistance(userLat, userLon, building.getLatitude(), building.getLongitude());

            //Log.d("DistanceDebug", building.getName()+ "  " + distance);
            }

        return closestBuildings;
    }


    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        // This function implemented from https://stackoverflow.com/questions/33825626/calculate-the-distance-between-two-geographical-coordinates-in-java

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 1.609344 * 1000;
        return (dist);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /* The function to convert radians into decimal */
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static Building[] createBuildingsFromJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Building[] buildings = new Building[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id"); // Extract the id
                String gpsCoords = jsonObject.getString("gps_coords");
                // Extract the numeric values correctly from the POINT string
                String coordsString = gpsCoords.substring(gpsCoords.indexOf('(') + 1, gpsCoords.lastIndexOf(')'));
                String[] coords = coordsString.trim().split("\\s+");
                // Check if the coords array has the expected number of elements
                if (coords.length >= 2) {
                    double longitude = Double.parseDouble(coords[0]);
                    double latitude = Double.parseDouble(coords[1]);

                    String buildingCode = jsonObject.getString("building_code");
                    String buildingName = jsonObject.getString("building_name");
                    String buildingNum = jsonObject.getString("building_num");
                    String department = jsonObject.getString("department");
                    String operatingHours = jsonObject.optString("operating_hours", "N/A"); // Use optString to handle missing "operating_hours"

                    // Pass the id when creating a new Building object
                    buildings[i] = new Building(id, buildingName, buildingCode, latitude, longitude, buildingNum, department, operatingHours);

                    // Log the created Building object
                    Log.d("BuildingCreated", "Created: " + buildings[i].getInfo());
                } else {
                    Log.e("Building", "Invalid GPS coordinates format: " + gpsCoords);
                }
            }

            return buildings;
        } catch (Exception e) {
            Log.e("Building", "Error parsing JSON", e);
            return null;
        }
    }
    public void populateRelatedClasses(JSONArray librariesJson, JSONArray restroomsJson, JSONArray vendingMachinesJson , JSONArray resourcesJson, JSONArray storeJson, JSONArray labJson, JSONArray elevatorJson,JSONArray staircaseJson, JSONArray specialFeaturesJson){
        this.libraries = Library.createLibrariesFromJson(librariesJson.toString());
        this.restrooms = Restroom.createRestroomsFromJson(restroomsJson.toString());
        this.vendingMachines = VendingMachine.createVendingMachinesFromJson(vendingMachinesJson.toString());
        this.resources = Resource.createResourcesFromJson(resourcesJson.toString());
        this.stores = Store.createStoreFromJson(storeJson.toString());
        this.labs = Lab.createLabsFromJson(labJson.toString());
        this.elevators = Elevator.createElevatorsFromJson(elevatorJson.toString());
        this.staircases = Staircase.createStaircasesFromJson(staircaseJson.toString());
        this.specialFeatures = SpecialFeature.createSpecialFeaturesFromJson(specialFeaturesJson.toString());
    }

}


