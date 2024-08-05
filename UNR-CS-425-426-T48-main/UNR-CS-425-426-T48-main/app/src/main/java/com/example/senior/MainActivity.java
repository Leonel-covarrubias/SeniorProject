package com.example.senior;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.example.senior.databinding.ActivityMainBinding;
import com.google.android.gms.location.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// Camera preview was implemented with CameraX documentation as reference

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ActivityResultLauncher<String[]> activityResultLauncher;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    //Webservice Constants
    private ProgressDialog pd;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    // Camera-related constants
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};

    private ActivityMainBinding binding;
    private TextView coordinatesTextView;
    private TextView azimuthTextView;
    LocationManager locationManager;
    private SensorManager sensorManager;

    private float[] accelerometerValues = new float[3];
    private float[] magnetometerValues = new float[3];

    private float previousAzimuth = 0.0f;

    private static final float AZIMUTH_THRESHOLD = 10.0f;
    private static final float ALPHA = 0.5f; // Adjust this value to control the amount of smoothing for Azimuth
    private float smoothedAzimuth = 0;

    private int consecutiveRecognitions = 0;
    private static final int REQUIRED_CONSECUTIVE_RECOGNITIONS = 3; // for building recognition accuracy

    private double latitude;
    private double longitude;

    private Building[] closestBuildings = new Building[4];
    private Building [] AllBuildings = new Building[99];
    private DiningOption [] AllDiningOptions = new DiningOption[225];
    private Library [] AllLibraries = new Library[225];
    private Hangoutspots[] AllHangoutspots = new Hangoutspots[255];
    private Restroom[] AllRestrooms = new Restroom[255];
    private VendingMachine[] AllVendingMachines = new VendingMachine[255];
    private Resource[] AllResources = new Resource[255];
    private Store[] AllStores = new Store[255];
    private Lab[] AllLabs = new Lab[255];
    private Elevator[] AllElevators = new Elevator[255];
    private Staircase[] AllStaircases = new Staircase[255];
    private SpecialFeature[] AllSpecialFeatures = new SpecialFeature[255];
    private int currentBuildingId;

    private Building LastSuccessfulBuilding;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String COLOR_BLINDNESS_MODE_KEY = "colorBlindnessMode";

    private int colorBlindnessMode = 0;

    private TextToSpeech textToSpeech;
    private static final String PREFS_NAME_NOTES = "UserNotesPrefs";
    private static final String USER_NOTES_KEY = "userNotes";

    private ArrayList<UserNotes> userNotesList = new ArrayList<>();
    private ArrayList<UserNotes> FiltereduserNotesList = new ArrayList<>();
    private ArrayAdapter<UserNotes> adapter;

    private static final String PREF_NAME_DEV = "Dev_options_pref";
    private static final String KEY_DEVELOPER_OPTIONS = "developer_options";

    private static SharedPreferences Devpreferences;

    private static final String PREF_NAME_MUTE = "Mute_options_pref";
    private static final String KEY_MUTE_OPTIONS = "Mute_options";
    private static SharedPreferences Mutepreferences;




    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (latitude == 0){
                return;
            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values;
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magnetometerValues = event.values;


                // Calculate azimuth when both accelerometer and magnetometer values are available
                if (accelerometerValues != null && magnetometerValues != null) {
                    float[] rotationMatrix = new float[9];
                    float[] orientationValues = new float[3];

                    // Get the rotation matrix from the accelerometer and magnetometer values
                    SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magnetometerValues);

                    // only horizontal movement used
                    float[] remappedRotationMatrix = new float[9];
                    SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix);


                    SensorManager.getOrientation(remappedRotationMatrix, orientationValues);


                    // finds raw azimuth
                    float azimuth = (float) Math.toDegrees(orientationValues[0]);

                    // Ensure azimuth is in the range
                    azimuth = (azimuth + 360) % 360;

                    // Apply a low-pass filter
                    smoothedAzimuth = smoothedAzimuth + ALPHA * (azimuth - smoothedAzimuth);

                    //Only changes the azimuth if a significant change happens
                    if (Math.abs(smoothedAzimuth - previousAzimuth) < 2) {
                        smoothedAzimuth = previousAzimuth;
                    }
                    // Update the UI
                    updateAzimuth(smoothedAzimuth);


                    for (Building building : closestBuildings) {
                    // Calculate bearing to the destination
                        float bearing = calculateBuildingBearing(building.getLatitude(), building.getLongitude());


                    // Check if the building is found based on the azimuth and bearing
                        if (Math.abs(smoothedAzimuth - bearing) < AZIMUTH_THRESHOLD) {
                            LastSuccessfulBuilding = building;
                            consecutiveRecognitions++;

                            if (consecutiveRecognitions >= REQUIRED_CONSECUTIVE_RECOGNITIONS) {
//                            // Reset the counter
                                consecutiveRecognitions = 0;
//
//                            // Building is found consistently, perform actions accordingly
                                buildingFound(building);}
                        }
                        else {
                            if(building == LastSuccessfulBuilding){
                                consecutiveRecognitions = 0;
                            }
                        }

                    }
                    previousAzimuth = smoothedAzimuth;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Handle accuracy changes if needed
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        coordinatesTextView = findViewById(R.id.coordinatesTextView);
        azimuthTextView = findViewById(R.id.azimuthTextView);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        colorBlindnessMode = prefs.getInt(COLOR_BLINDNESS_MODE_KEY, 0);
        applyColorBlindMode(colorBlindnessMode);

        ListView listViewNotes = findViewById(R.id.notesListView);
        adapter = new ArrayAdapter<>(this, R.layout.popup_note_list, FiltereduserNotesList);
        listViewNotes.setAdapter(adapter);

       Devpreferences = getSharedPreferences(PREF_NAME_DEV, Context.MODE_PRIVATE);
       CheckDevView();

       Mutepreferences = getSharedPreferences(PREF_NAME_MUTE, Context.MODE_PRIVATE);



        //AllBuildings = new Building(null, null,0,0).AddAllBuildings();

        // Webservice
        //Log.d("WebServiceResult", "String Returned: " +  connectToWebService("http://34.41.18.211/webservices/Building/"));
        String jsonBuildingData = connectToWebService("http://34.41.18.211/webservices/Building/");
        AllBuildings = Building.createBuildingsFromJson(jsonBuildingData);
        //Log.d("WebServiceResult", "String Returned: " +  connectToWebService("http://34.41.18.211/webservices/dining_option/"));
        String jsonDiningData = connectToWebService("http://34.41.18.211/webservices/dining_option/");
        AllDiningOptions = DiningOption.createDiningOptionsFromJson(jsonDiningData);
        String jsonLibraryData = connectToWebService("http://34.41.18.211/webservices/libraries/");
        AllLibraries = Library.createLibrariesFromJson(jsonLibraryData);
        String jsonHangoutspotsData = connectToWebService("http://34.41.18.211/webservices/hangoutspots/");
        AllHangoutspots = Hangoutspots.createHangoutSpotsFromJson(jsonHangoutspotsData);
        String jsonRestroomsData = connectToWebService("http://34.41.18.211/webservices/restrooms/");
        AllRestrooms = Restroom.createRestroomsFromJson(jsonRestroomsData);
        String jsonVendingMachineData = connectToWebService("http://34.41.18.211/webservices/vendingmachines/");
        AllVendingMachines = VendingMachine.createVendingMachinesFromJson(jsonVendingMachineData);
        String jsonResourcesData = connectToWebService("http://34.41.18.211/webservices/resources/");
        AllResources = Resource.createResourcesFromJson(jsonResourcesData);
        String jsonStoreData = connectToWebService("http://34.41.18.211/webservices/store/");
        AllStores = Store.createStoreFromJson(jsonStoreData);
        String jsonLabData = connectToWebService("http://34.41.18.211/webservices/labs/");
        AllLabs = Lab.createLabsFromJson(jsonLabData);
        String jsonElevatorData = connectToWebService("http://34.41.18.211/webservices/elevators/");
        AllElevators = Elevator.createElevatorsFromJson(jsonElevatorData);
        String jsonStaircaseData = connectToWebService("http://34.41.18.211/webservices/staircases/");
        AllStaircases = Staircase.createStaircasesFromJson(jsonStaircaseData);
        String jsonSpecialFeaturesData = connectToWebService("http://34.41.18.211/webservices/special_features/");
        AllSpecialFeatures = SpecialFeature.createSpecialFeaturesFromJson(jsonSpecialFeaturesData);

        //connectToWebService("http://34.41.18.211/webservices/dining_option/");
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TTS engine initialized successfully
                } else {
                    // TTS engine initialization failed
                    Toast.makeText(MainActivity.this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Location permissions
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else{
             getLocation();
        }

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i = new Intent(MainActivity.this,Search_activity.class);
               startActivity(i);
            }

        });
        binding.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SEARCH BUTTON FUNCTIONALITY WILL GO HERE
                Intent i = new Intent(MainActivity.this,MainMenu.class);
                startActivity(i);
            }

        });
        binding.MoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the current scanned building ID to the MoreInfo activity
                Intent intent = new Intent(MainActivity.this, MoreInfo.class);
                intent.putExtra("building_id", currentBuildingId);
                startActivity(intent);
            }
        });
        binding.resetEnvironment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               UndoShowBottomButtons();
               HidePopups();
            }

        });

        // Initialize the ActivityResultLauncher for requesting permissions
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                this::handlePermissionsResult);

        // Check and request permissions when needed
        checkAndRequestPermissions();
        registerSensorListeners();
    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        // Create a location request
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000); // Update interval in milliseconds

        // Create location callback
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    updateCoordinatesTextView(latitude,longitude);
                    GetclosestBuildings();
                }
            }
        };

        // Request location updates
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        client.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void checkAndRequestPermissions() {
        // Check if the required permissions are granted
        Map<String, Boolean> permissionsMap = new HashMap<>();
        for (String permission : REQUIRED_PERMISSIONS) {
            boolean isPermissionGranted =
                    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
            permissionsMap.put(permission, isPermissionGranted);
        }

        // Request permissions if not all are granted
        boolean shouldRequestPermissions = permissionsMap.values().stream().anyMatch(granted -> !granted);

        if (shouldRequestPermissions) {
            // Request permissions using the ActivityResultLauncher
            activityResultLauncher.launch(REQUIRED_PERMISSIONS);
        } else {
            //Permissions Granted,
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // catch

                }
            }, ContextCompat.getMainExecutor(this));
        }
    }

    private void handlePermissionsResult(Map<String, Boolean> permissions) {
        // Handle Permission granted/rejected
        boolean permissionGranted = true;
        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
            if (entry.getKey().equals(Manifest.permission.CAMERA) && !entry.getValue()) {
                permissionGranted = false;
                break;
            }
        }

        if (!permissionGranted) {
            Toast.makeText(this, "Permission request denied", Toast.LENGTH_SHORT).show();
        }
    }
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        PreviewView previewView = findViewById(R.id.previewView);

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
    }

    private void updateCoordinatesTextView(double latitude, double longitude) {
        if (coordinatesTextView != null) {
            String coordinatesText = "Latitude: " + latitude + "\nLongitude: " + longitude;
            coordinatesTextView.setText(coordinatesText);
        }}
    private void registerSensorListeners() {
        // Register the accelerometer and magnetometer sensor listeners
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (accelerometerSensor != null && magnetometerSensor != null) {
            sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(sensorEventListener, magnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Handle the case where one or both sensors are not available
            Toast.makeText(this, "Accelerometer or magnetometer not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void unregisterSensorListeners() {
        // Unregister the sensor listeners to conserve resources
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void updateAzimuth(float azimuth) {
        // Update the UI or perform any other actions with the azimuth
        azimuthTextView.setText("Azimuth: " + azimuth + " degrees");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            // Update the text view with location coordinates
            updateCoordinatesTextView(latitude, longitude);
        }

        GetclosestBuildings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensors and location updates when the activity is paused
        unregisterSensorListeners();
    }

    private float calculateBuildingBearing(double buildingLat, double buildingLon) {
        Location userLocation = new Location("UserLocation");
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);

        Location buildingLocation = new Location("BuildingLocation");
        buildingLocation.setLatitude(buildingLat);
        buildingLocation.setLongitude(buildingLon);

        // Calculate the bearing from the user's location to the building
        float bearing = userLocation.bearingTo(buildingLocation);

        // Adjust the bearing to be in the range [0, 360)
        if (bearing < 0) {
            bearing += 360;
        }

        return bearing;
    }

    private void buildingFound(Building building) {
        // Building is found, perform actions accordingly
        speak(building.getName());
        ShowBottomButtons();
        unregisterSensorListeners();
        ShowPopups(building);
        currentBuildingId = building.getId();


    }
    private void ShowBottomButtons(){
        TextView description = findViewById(R.id.tvTipDescription);
        ImageView icon = findViewById(R.id.infoIcon);

        description.setVisibility(View.GONE);
        icon.setVisibility(View.GONE);

        binding.resetEnvironment.setVisibility(View.VISIBLE);
        binding.MoreInfo.setVisibility(View.VISIBLE);




    }
    private void UndoShowBottomButtons() {
        TextView description = findViewById(R.id.tvTipDescription);
        ImageView icon = findViewById(R.id.infoIcon);

        description.setVisibility(View.VISIBLE);
        icon.setVisibility(View.VISIBLE);

        registerSensorListeners();
        binding.resetEnvironment.setVisibility(View.INVISIBLE);
        binding.MoreInfo.setVisibility(View.INVISIBLE);
    }
    private void GetclosestBuildings() {
        // Assuming 'AllBuildings' is already populated with Building objects
        if (AllBuildings.length > 0) {
            closestBuildings = AllBuildings[0].getClosestBuildings(latitude, longitude, AllBuildings);
        }
    }


        private void ShowPopups(Building building) {
            TextView name = findViewById(R.id.buildingNameTextView);
            TextView code = findViewById(R.id.buildingCodeTextView);
            TextView hours = findViewById(R.id.buildingHoursTextView);

            name.setText(building.getName());
            code.setText(building.getBuildingCode());
            hours.setText(building.getOperatingHours());
            binding.buildingInfoLayout.setVisibility(View.VISIBLE);
            binding.buildingHoursLayout.setVisibility(View.VISIBLE);
            retrieveUserNotes(building);
            if(!(FiltereduserNotesList.isEmpty())){
                binding.notesLayout.setVisibility(View.VISIBLE);}



        }

    private void HidePopups() {
        binding.notesLayout.setVisibility(View.INVISIBLE);
        binding.buildingInfoLayout.setVisibility(View.INVISIBLE);
        binding.buildingHoursLayout.setVisibility(View.INVISIBLE);
    }

    private void applyColorBlindMode(int colorBlindnessMode) {
        // Apply color blindness filter
        ColorBlind.applyColorBlindMode(getWindow().getDecorView().getRootView(), colorBlindnessMode);
    }
    private void speak(String text) {

        if (textToSpeech != null &&  Mutepreferences.getBoolean(KEY_MUTE_OPTIONS, false) != false) {
            // Speak the text
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    private void retrieveUserNotes(Building building) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME_NOTES, Context.MODE_PRIVATE);
        String userNotesJson = prefs.getString(USER_NOTES_KEY, null);
        if (userNotesJson != null) {
            Type listType = new TypeToken<ArrayList<UserNotes>>(){}.getType();
            userNotesList.clear();
            userNotesList.addAll(new Gson().fromJson(userNotesJson, listType));
            adapter.notifyDataSetChanged();
        }
        FiltereduserNotesList.clear();
        for (UserNotes note : userNotesList) {
            if (note.getBuildingName().equals(building.getName())) {
                FiltereduserNotesList.add(note);
            }
        }
    }
    //Webservices Code
    public String connectToWebService(String urlString) {
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);
        pd.show();

        Future<String> future = executorService.submit(() -> {
            String result = null;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                result = buffer.toString();
            } catch (MalformedURLException e) {
                Log.e("WebServiceError", "Malformed URL Exception: " + e.getMessage(), e);
            } catch (IOException e) {
                Log.e("WebServiceError", "IO Exception: " + e.getMessage(), e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        });

        try {
            // Get the result from the future. This will block until the callable has completed.
            String result = future.get();
            pd.dismiss();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void CheckDevView(){
        if (Devpreferences.getBoolean(KEY_DEVELOPER_OPTIONS, false) == false){
            binding.coordinatesTextView.setVisibility(View.INVISIBLE);
            binding.azimuthTextView.setVisibility(View.INVISIBLE);
        }
    }

}

