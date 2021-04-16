package com.adi.teleprompter.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adi.teleprompter.databinding.OpenedDocumentFragmentBinding;

import static com.adi.teleprompter.views.MainActivity.BACKGROUND_COLOR;
import static com.adi.teleprompter.views.MainActivity.BUNDLE_LAST_URI;
import static com.adi.teleprompter.views.MainActivity.DOCUMENT_URI_SELECTED;
import static com.adi.teleprompter.views.dialogs.SpeedPickerFragment.ENTERED_SPEED;
import static com.adi.teleprompter.views.dialogs.SpeedPickerFragment.ENTERED_TEXT_SIZE;

public class OpenedDocumentFragment extends Fragment {
    public static final String GET_DISTANCE = "GET_DISTANCE";
    public static final String GET_SPEED = "GET_SPEED";
    private OpenedDocumentFragmentBinding openedDocumentFragmentBinding;
    String textToDisplay;
    float speed;
    float textSize;
    int backgroundColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        openedDocumentFragmentBinding = OpenedDocumentFragmentBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(DOCUMENT_URI_SELECTED, Context.MODE_PRIVATE);
        if (bundle != null) {
            textToDisplay = bundle.getString(BUNDLE_LAST_URI);
            speed = Float.parseFloat(sharedPreferences.getString(ENTERED_SPEED, "1"));
            textSize = Float.parseFloat(sharedPreferences.getString(ENTERED_TEXT_SIZE, "18"));
            backgroundColor = sharedPreferences.getInt(BACKGROUND_COLOR, 0);
            if (textToDisplay != null && savedInstanceState == null) {
                openedDocumentFragmentBinding.tvMain.setSpeed(speed);
                openedDocumentFragmentBinding.tvMain.setTextSize(textSize);
                openedDocumentFragmentBinding.tvMain.setText(textToDisplay.trim());
                openedDocumentFragmentBinding.tvMain.setBackgroundColor(backgroundColor);
                openedDocumentFragmentBinding.svMain.setBackgroundColor(backgroundColor);
            }
        }
        return openedDocumentFragmentBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_LAST_URI, textToDisplay);
//        outState.putInt(GET_DISTANCE, openedDocumentFragmentBinding.tvMain.getOffset());
        outState.putFloat(GET_SPEED, openedDocumentFragmentBinding.tvMain.getSpeed());
        Log.i("AA_", "onSaveInstanceState: "+openedDocumentFragmentBinding.tvMain.getInfo());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (savedInstanceState != null) {
            openedDocumentFragmentBinding.tvMain.setSpeed(savedInstanceState.getFloat(GET_SPEED));
            openedDocumentFragmentBinding.tvMain.setTextSize(textSize);
            openedDocumentFragmentBinding.tvMain.setText(savedInstanceState.getString(BUNDLE_LAST_URI));
            openedDocumentFragmentBinding.tvMain.setBackgroundColor(backgroundColor);
            openedDocumentFragmentBinding.svMain.setBackgroundColor(backgroundColor);

        }
    }
}
