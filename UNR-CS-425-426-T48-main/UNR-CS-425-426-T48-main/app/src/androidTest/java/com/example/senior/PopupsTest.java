package com.example.senior;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

@RunWith(AndroidJUnit4.class)
public class PopupsTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testShowPopups() {
        // Calls the function being test on the main thread
        MainActivity mainActivity = activityTestRule.getActivity();

        // Checks that the popups are visible
        ViewInteraction buildingInfoLayout = onView(withId(R.id.buildingInfoLayout));
        buildingInfoLayout.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        ViewInteraction buildingHoursLayout = onView(withId(R.id.buildingHoursLayout));
        buildingHoursLayout.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }
}