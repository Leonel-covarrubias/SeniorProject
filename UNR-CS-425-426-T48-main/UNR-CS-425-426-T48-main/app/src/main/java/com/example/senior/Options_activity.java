package com.example.senior;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.senior.databinding.ActivityOptionsBinding;
import com.google.common.util.concurrent.ListenableFuture;


public class Options_activity extends AppCompatActivity {

    private ActivityResultLauncher<String[]> activityResultLauncher;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    // Camera-related constants
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};

    private ActivityOptionsBinding binding;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String COLOR_BLINDNESS_MODE_KEY = "colorBlindnessMode";
    private int colorBlindnessMode = 0;

    private static final String PREF_NAME_DEV = "Dev_options_pref";
    private static final String KEY_DEVELOPER_OPTIONS = "developer_options";

    private static SharedPreferences preferences;
    private boolean developerOptionsEnabled;


    private static final String PREF_NAME_MUTE = "Mute_options_pref";
    private static final String KEY_MUTE_OPTIONS = "Mute_options";

    private static SharedPreferences Mutepreferences;
    private boolean MuteEnabled;

    private static final String PREF_NAME_ACCESSIBILITY = "Accessibility_options_pref";
    private static final String KEY_ACCESSIBILITY_OPTIONS = "accessibility_options";
    private static SharedPreferences Accessibilitypreferences;
    private boolean AccessibilityEnabled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Restore the selected color blindness mode from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        colorBlindnessMode = prefs.getInt(COLOR_BLINDNESS_MODE_KEY, 0);
        applyColorBlindMode(colorBlindnessMode);

        preferences = getSharedPreferences(PREF_NAME_DEV, Context.MODE_PRIVATE);
        Mutepreferences = getSharedPreferences(PREF_NAME_MUTE, Context.MODE_PRIVATE);
        Accessibilitypreferences = getSharedPreferences(PREF_NAME_ACCESSIBILITY, Context.MODE_PRIVATE);
        updateDevButton();
        updateMuteButton();
        updateAccessibilityButton();

        binding.exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Options_activity.this, MainMenu.class);
                startActivity(i);
            }
        });

        binding.colorblindbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle color blindness mode
                toggleColorBlindMode();

                // Update SharedPreferences with the new color blindness mode
                SharedPreferences preferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("colorBlindnessMode", colorBlindnessMode);
                editor.apply();

                // Apply the color blindness mode immediately after updating SharedPreferences
                applyColorBlindMode(colorBlindnessMode);
            }
        });

        binding.resetoptionsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorBlindnessMode = 0;

                // Update SharedPreferences with the new color blindness mode
                SharedPreferences p = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = p.edit();
                editor.putInt("colorBlindnessMode", colorBlindnessMode);
                editor.apply();

                SharedPreferences.Editor e = preferences.edit();
                e.putBoolean(KEY_DEVELOPER_OPTIONS, false);
                e.apply();
                updateDevButton();

                SharedPreferences.Editor r = Mutepreferences.edit();
                r.putBoolean(KEY_MUTE_OPTIONS, true);
                r.apply();
                updateMuteButton();

                SharedPreferences.Editor a = Accessibilitypreferences.edit();
                a.putBoolean(KEY_ACCESSIBILITY_OPTIONS, false);
                a.apply();
                updateAccessibilityButton();


                // Apply the color blindness mode immediately after updating SharedPreferences
                applyColorBlindMode(colorBlindnessMode);
            }
        });

        binding.devoptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                developerOptionsEnabled = !developerOptionsEnabled;
                setDeveloperOptionsEnabled(developerOptionsEnabled);
                updateDevButton();
            }
        });
        binding.TextToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MuteEnabled = !MuteEnabled;
                setMuteEnabled(MuteEnabled);
                updateMuteButton();
            }
        });

        binding.Accessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessibilityEnabled = !AccessibilityEnabled;
                setAccessibilityEnabled(AccessibilityEnabled);
                updateAccessibilityButton();
            }
        });

        binding.myIntructors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if button clicked, go to the notes fragment
                Fragment notesFragment = new NotesFragment();


                FragmentManager fragmentManager = getSupportFragmentManager();


                FragmentTransaction transaction = fragmentManager.beginTransaction();


                transaction.replace(android.R.id.content, notesFragment);


                transaction.commit();

            }
        });



    }
    private void toggleColorBlindMode() {
        // Cycle through the different types of color blindness
        colorBlindnessMode = (colorBlindnessMode + 1) % 4;
    }

    private void applyColorBlindMode(int colorBlindnessMode) {
        // Apply color blindness filter
        ColorBlind.applyColorBlindMode(getWindow().getDecorView().getRootView(), colorBlindnessMode);
    }

    private boolean isDeveloperOptionsEnabled() {
        return preferences.getBoolean(KEY_DEVELOPER_OPTIONS, false);
    }

    private void setDeveloperOptionsEnabled(boolean enabled) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_DEVELOPER_OPTIONS, enabled);
        editor.apply();
    }

    private void updateDevButton() {
        Button toggleButton = findViewById(R.id.devoptions);
        if (isDeveloperOptionsEnabled()) {
            toggleButton.setText("DEV OPTIONS ON");
            toggleButton.setTextColor(Color.WHITE);
        } else {
            toggleButton.setText("DEV OPTIONS OFF");
            toggleButton.setTextColor(Color.RED);

        }
    }
    private void updateMuteButton() {
        Button toggleButton = findViewById(R.id.TextToSpeech);
        if (isMuteEnabled()) {
            toggleButton.setText("TEXT-TO-SPEECH ON");
            toggleButton.setTextColor(Color.WHITE);
        } else {
            toggleButton.setText("TEXT-TO-SPEECH OFF");
            toggleButton.setTextColor(Color.RED);
        }
    }
    private void setMuteEnabled(boolean enabled) {
        SharedPreferences.Editor editor = Mutepreferences.edit();
        editor.putBoolean(KEY_MUTE_OPTIONS, enabled);
        editor.apply();
    }
    private boolean isMuteEnabled() {
        return Mutepreferences.getBoolean(KEY_MUTE_OPTIONS, false);
    }

    private void updateAccessibilityButton() {
        Button toggleButton = findViewById(R.id.Accessibility);
        if (isAccessibilityEnabled()) {
            toggleButton.setText("ACCESSIBILITY ON");
            toggleButton.setTextColor(Color.WHITE);
        } else {
            toggleButton.setText("ACCESSIBILITY OFF");
            toggleButton.setTextColor(Color.RED);
        }
    }

    private void setAccessibilityEnabled(boolean enabled) {
        SharedPreferences.Editor editor = Accessibilitypreferences.edit();
        editor.putBoolean(KEY_ACCESSIBILITY_OPTIONS, enabled);
        editor.apply();
    }

    public boolean isAccessibilityEnabled() {
        if (Accessibilitypreferences!= null) {
            return Accessibilitypreferences.getBoolean(KEY_ACCESSIBILITY_OPTIONS, true);
        } else {
            return false;
        }
    }
}