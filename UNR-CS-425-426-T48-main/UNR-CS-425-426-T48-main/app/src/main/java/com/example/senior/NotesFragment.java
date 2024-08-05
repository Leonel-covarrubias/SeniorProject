package com.example.senior;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private static final String PREFS_NAME = "UserNotesPrefs";
    private static final String USER_NOTES_KEY = "userNotes";
    private static final String PREFS_NAME_COLOR = "MyPrefsFile";
    private static final String COLOR_BLINDNESS_MODE_KEY = "colorBlindnessMode";

    private int colorBlindnessMode = 0;

    private ArrayList<UserNotes> userNotesList = new ArrayList<>();
    private ArrayAdapter<UserNotes> adapter;



    private int lastClickedPosition = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        ListView listViewNotes = view.findViewById(R.id.listViewNotes);
        adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item_note, userNotesList);
        listViewNotes.setAdapter(adapter);

        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME_COLOR, Context.MODE_PRIVATE);
        colorBlindnessMode = prefs.getInt(COLOR_BLINDNESS_MODE_KEY, 0);
        ColorBlind.applyColorBlindMode(listViewNotes, colorBlindnessMode);



        //array of building names
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



        ArrayAdapter<String> buildingAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, buildingNames);


        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Spinner spinnerBuildingName = view.findViewById(R.id.spinnerBuildingName);
        spinnerBuildingName.setAdapter(buildingAdapter);


        Button addButton = view.findViewById(R.id.addButton);
        ColorBlind.applyColorBlindMode(addButton, colorBlindnessMode);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteToList();
                InputMethodManager HideKeyboard = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                HideKeyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        Button backButton = view.findViewById(R.id.backButton);
        ColorBlind.applyColorBlindMode(backButton, colorBlindnessMode);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager HideKeyboard = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                HideKeyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                getParentFragmentManager().beginTransaction().hide(NotesFragment.this).commit();            }
        });

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lastClickedPosition == position) {
                    //Double click to remove from list
                    userNotesList.remove(position);

                    adapter.notifyDataSetChanged();

                    saveUserNotes();

                    lastClickedPosition = -1;

                    Toast.makeText(getContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();

                } else {

                    lastClickedPosition = position;
                    Toast.makeText(getContext(), "Click again to delete", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // Retrieve user notes from SharedPreferences
        retrieveUserNotes();

        return view;
    }

    private void addNoteToList() {
        EditText editTextName = requireView().findViewById(R.id.editTextName);
        EditText editTextRoomNumber = requireView().findViewById(R.id.editTextRoomNumber);
        Spinner spinnerBuildingName = requireView().findViewById(R.id.spinnerBuildingName);

        String name = editTextName.getText().toString().trim();
        String roomNumberString = editTextRoomNumber.getText().toString().trim();
        String buildingName = spinnerBuildingName.getSelectedItem().toString();

        if (name.isEmpty() || roomNumberString.isEmpty() || buildingName.equals("Select A Building")) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int roomNumber = Integer.parseInt(roomNumberString);
        UserNotes note = new UserNotes(name, roomNumber, buildingName);
        userNotesList.add(note);
        adapter.notifyDataSetChanged();


        editTextName.getText().clear();
        editTextRoomNumber.getText().clear();
        spinnerBuildingName.setSelection(0);

        // Save user notes to SharedPreferences
        saveUserNotes();
    }

    private void saveUserNotes() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String userNotesJson = new Gson().toJson(userNotesList);
        editor.putString(USER_NOTES_KEY, userNotesJson);
        editor.apply();
    }

    private void retrieveUserNotes() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userNotesJson = prefs.getString(USER_NOTES_KEY, null);
        if (userNotesJson != null) {
            Type listType = new TypeToken<ArrayList<UserNotes>>(){}.getType();
            userNotesList.clear();
            userNotesList.addAll(new Gson().fromJson(userNotesJson, listType));
            adapter.notifyDataSetChanged();
        }
    }
    private void applyColorBlindMode(int colorBlindnessMode) {
        // Apply color blindness filter

        ColorBlind.applyColorBlindMode(requireView(), colorBlindnessMode);
    }

}
