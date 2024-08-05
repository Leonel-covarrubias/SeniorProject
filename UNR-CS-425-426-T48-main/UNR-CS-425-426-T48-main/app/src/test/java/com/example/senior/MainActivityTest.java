package com.example.senior;

import org.junit.Test;

import static org.junit.Assert.*;
import android.location.Location;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

import org.junit.Test;

public class MainActivityTest {

    @Test
    public void testCalculateBuildingBearing() {

        double userLat = 40.7128;
        double userLon = -74.0060;


        double buildingLat = 40.7306;
        double buildingLon = -74.0060;


        float expectedBearing = 0.0f;


        float actualBearing = calculateBuildingBearing(userLat, userLon, buildingLat, buildingLon);

        // Assert
        assertEquals(expectedBearing, actualBearing, 0.01);
    }

    // Method to calculate building bearing
    private float calculateBuildingBearing(double userLat, double userLon, double buildingLat, double buildingLon) {
        // Create Location objects for user and building
        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(userLat);
        double lon1Rad = Math.toRadians(userLon);
        double lat2Rad = Math.toRadians(buildingLat);
        double lon2Rad = Math.toRadians(buildingLon);

        // Calculate differences in latitudes and longitudes
        double dLon = lon2Rad - lon1Rad;

        // Calculate initial bearing
        double y = Math.sin(dLon) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(dLon);
        double initialBearing = Math.atan2(y, x);

        // Convert initial bearing from radians to degrees
        initialBearing = Math.toDegrees(initialBearing);

        // Normalize initial bearing to the range [0, 360)
        initialBearing = (initialBearing + 360) % 360;

        return (float) initialBearing;

    }
}
