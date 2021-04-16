package com.adi.teleprompter.views.dialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.adi.teleprompter.TeleprompterApplication;
import com.adi.teleprompter.databinding.SpeedDialogFragmentBinding;
import com.adi.teleprompter.di.TeleprompterAppComponent;

import javax.inject.Inject;

import static com.adi.teleprompter.views.MainActivity.DOCUMENT_URI_SELECTED;

public class SpeedPickerFragment extends DialogFragment {

    public static final String ENTERED_SPEED = "ENTERED_SPEED";
    public static final String ENTERED_TEXT_SIZE = "Entered_TextSize";
    SpeedDialogFragmentBinding speedDialogFragmentBinding;
    private String speed;
    private String textSize;
    SharedPreferences.Editor sharedPrefsEditor;
    String titleText;

    @Inject
    Context context;

    public SpeedPickerFragment(String titleText) {
        this.titleText = titleText;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TeleprompterApplication.getAppComponent().inject(this);
        speedDialogFragmentBinding = SpeedDialogFragmentBinding.inflate(inflater, container, false);
        sharedPrefsEditor = context.getSharedPreferences(DOCUMENT_URI_SELECTED, Context.MODE_PRIVATE).edit();
        speedDialogFragmentBinding.etTextSpeed.setHint(titleText);
        speedDialogFragmentBinding.btSubmitSpeed.setOnClickListener(view -> {
            if (titleText.equals("Change Speed")) {
                speed =  speedDialogFragmentBinding.etTextSpeed.getText().toString();
                sharedPrefsEditor.putString(ENTERED_SPEED, speed);
            } else {
                textSize =  speedDialogFragmentBinding.etTextSpeed.getText().toString();
                sharedPrefsEditor.putString(ENTERED_TEXT_SIZE, textSize);
            }
            sharedPrefsEditor.apply();
            dismiss();
        });
        return speedDialogFragmentBinding.getRoot();
    }
}
