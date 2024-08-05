package com.example.senior;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorBlind {

    // Color matrices for protanopia, tritanopia, and achromatopsia
    private static final float[] protanopiaMatrix = {
            0.567f, 0.433f, 0.0f, 0.0f, 0.0f,
            0.558f, 0.442f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.242f, 0.758f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 1.0f
    };

    private static final float[] tritanopiaMatrix = {
            0.967f, 0.033f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.733f, 0.267f, 0.0f, 0.0f,
            0.0f, 0.183f, 0.817f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 1.0f
    };

    private static final float[] achromatopsiaMatrix = {
            0.33f, 0.33f, 0.33f, 0.0f, 0.0f,
            0.33f, 0.33f, 0.33f, 0.0f, 0.0f,
            0.33f, 0.33f, 0.33f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 1.0f
    };

    private static float[] originalMatrix() {
        return new float[]{
                1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f, 1.0f
        };
    }

    public static void applyColorBlindMode(View view, int colorBlindnessMode) {
        float[] colorMatrix = getColorMatrix(colorBlindnessMode);

        // Apply the color matrix to the view and its children
        applyColorBlindModeToView(view, colorMatrix);
        if (view instanceof ViewGroup) {
            applyColorBlindModeToChildren((ViewGroup) view, colorMatrix);
        }
    }



    private static void applyColorBlindModeToChildren(ViewGroup viewGroup, float[] colorMatrix) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            applyColorBlindModeToView(child, colorMatrix);
            if (child instanceof ViewGroup) {
                // If the child is a ViewGroup, apply the color matrix to its children recursively
                applyColorBlindModeToChildren((ViewGroup) child, colorMatrix);
            }
        }
    }

    private static void applyColorBlindModeToView(View view, float[] colorMatrix) {
        if (view != null) {
            // Apply the color matrix to the background
            Drawable background = view.getBackground();
            if (background != null) {
                background.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            }

            // Apply the color matrix to image views
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            }

            // Apply the color matrix to text views
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                ColorStateList colors = textView.getTextColors();
                if (colors != null) {
                    int[] colorState = textView.getDrawableState();
                    int filteredColor = colors.getColorForState(colorState, Color.BLACK);
                    textView.setTextColor(filteredColor);
                }
            }
        }
    }


    private static float[] getColorMatrix(int colorBlindnessMode) {
        switch (colorBlindnessMode) {
            case 1:
                return protanopiaMatrix;
            case 2:
                return tritanopiaMatrix;
            case 3:
                return achromatopsiaMatrix;
            default:
                return originalMatrix();
        }
    }
}
