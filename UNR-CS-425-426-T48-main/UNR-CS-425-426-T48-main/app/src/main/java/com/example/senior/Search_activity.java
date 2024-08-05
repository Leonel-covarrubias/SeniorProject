package com.example.senior;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.lifecycle.ProcessCameraProvider;
import com.example.senior.databinding.ActivitySearchBinding;
import com.google.common.util.concurrent.ListenableFuture;


public class Search_activity extends AppCompatActivity {

    private ActivityResultLauncher<String[]> activityResultLauncher;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    // Camera-related constants
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};

    private ActivitySearchBinding binding;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String COLOR_BLINDNESS_MODE_KEY = "colorBlindnessMode";

    private int colorBlindnessMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        colorBlindnessMode = prefs.getInt(COLOR_BLINDNESS_MODE_KEY, 0);
        applyColorBlindMode(colorBlindnessMode);


        String[] buildingNames = {"Select A Building",
                "Agricultural Education Health Building",
                "Anderson Health Science",
                "Ansari Business Building",
                "Applied Research Facility",
                "Argental Hall",
                "Artemisia Building",
                "BCN Purchasing",
                "Building 058",
                "Canada Hall",
                "Central Services",
                "Center for Molecular Medicine",
                "Chemistry Building",
                "Child Care Facility",
                "Church Fine Arts",
                "Clark Administration",
                "Claude Howard System Administration (NSHE)",
                "Computer Center",
                "David Hall",
                "Davidson Math and Science Center",
                "Department of Pediatrics",
                "Dining Conference Center",
                "Earthquake Engineering Laboratory",
                "Edmund J. Cain Hall",
                "Effie Mona Mack",
                "Environmental Research Facility",
                "E. L. Wiegand Fitness Center",
                "Equestrian Center",
                "Edna S. Brigham Clinical Education Building",
                "Facilities Maintenance Services Receiving Warehouse",
                "Facilities Services",
                "Fitzgerald Student Services Building",
                "Fleischmann Agriculture",
                "Fleischmann Planetarium",
                "Frandsen Humanities",
                "Gateway Parking Complex",
                "Great Basin Hall",
                "Harry Reid Engineering Laboratory",
                "Holman Arts & Media Center",
                "Howard Medical Sciences",
                "Innevation Center",
                "Joe Crowley Student Union",
                "Jones Center",
                "Jot Travis Building",
                "Juniper Hall",
                "Knudtsen Resource Center",
                "Lawlor Events Center",
                "Leifson Physics",
                "Life Science",
                "Lincoln Hall",
                "Lombardi Recreation Center",
                "Mackay Mines",
                "Mackay Science",
                "Mackay Stadium",
                "Manville Health Science",
                "Manzanita Hall",
                "Mathewson-IGT Knowledge Center",
                "Morrill Hall",
                "Motor Pool",
                "National Judicial College Building",
                "Nell J. Redfield (Student Health Center)",
                "Nell J. Redfield Building A, Redfield Campus",
                "Nellor Biomedical Sciences",
                "Nevada Living Learning Community",
                "Nevada State Health Laboratory",
                "Nye Hall",
                "Orvis Building",
                "Palmer Engineering",
                "Parking Services",
                "Patterson Hall",
                "Paul Laxalt Mineral Engineering",
                "Paul Laxalt Mineral Research",
                "Peavine Hall",
                "William N. Pennington Engineering Building",
                "William N. Pennington Health Sciences Building",
                "William N. Pennington Medical Education",
                "William N. Pennington Student Achievement Center",
                "Marguerite W. Petersen Athletic Academic Center",
                "Ponderosa Village",
                "Prim Library",
                "Prim-Schultz Hall",
                "Real Estate Office",
                "Renewable Resource Center",
                "PBS Reno",
                "Reynolds School of Journalism",
                "Robert Cashell Fieldhouse",
                "Ross Hall",
                "Sarah H. Fleischmann Building",
                "Savitt Medical Science",
                "Schulich Lecture Hall",
                "Sierra Hall",
                "Sierra Street Parking Complex",
                "Sports Medicine Complex",
                "Tahoe Center for Environmental Sciences",
                "Thompson Building",
                "University Foundation Arts",
                "Utility Plant",
                "Valley Road Greenhouse Complex",
                "Virginia Street Gym",
                "Visitors Locker Room",
                "West Stadium Parking Complex",
                "Brian J. Whalen Parking Complex",
                "William J. Raggio",
                "William Peccole Park"
        };



        ArrayAdapter<String> buildingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingNames);


        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Spinner spinnerBuildingName = findViewById(R.id.spinnerBuildingName);
        spinnerBuildingName.setAdapter(buildingAdapter);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity, which will navigate back to the previous activity
                finish();
            }
        });
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String buildingName = spinnerBuildingName.getSelectedItem().toString();
                spinnerBuildingName.setSelection(0);

                Intent intent = new Intent(Search_activity.this, MoreInfo.class);
                intent.putExtra("building_name", buildingName);
                startActivity(intent);

                Toast.makeText(Search_activity.this, "Fetching "+buildingName, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void applyColorBlindMode(int colorBlindnessMode) {
        // Apply color blindness filter
        ColorBlind.applyColorBlindMode(getWindow().getDecorView().getRootView(), colorBlindnessMode);
    }
}
