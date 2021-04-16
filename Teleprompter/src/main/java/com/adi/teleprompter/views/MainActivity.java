package com.adi.teleprompter.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.adi.teleprompter.R;
import com.adi.teleprompter.TeleprompterApplication;
import com.adi.teleprompter.databinding.ActivityMainBinding;
import com.adi.teleprompter.db.TextFileDatabase;
import com.adi.teleprompter.model.TextFile;
import com.adi.teleprompter.views.dialogs.SavedListDialogFragment;
import com.adi.teleprompter.views.dialogs.SpeedPickerFragment;
import com.adi.teleprompter.views.viewmodels.MainViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.adi.teleprompter.recievers.FilesWidgetProvider.IS_FROM_WIDGET;

public class MainActivity extends AppCompatActivity {
    public static final String DOCUMENT_URI_SELECTED = "DOCUMENT_URI_SELECTED";
    public static final String BUNDLE_LAST_URI = "BUNDLE_LAST_URI";
    public static final String IV_IMPORT_VISIBILITY = "IV_IMPORT_VISIBILITY";
    public static final String TOOLBAR_VISIBILITY = "TOOLBAR_VISIBILITY";
    public static final String TEXT_DATA = "TEXT_DATA";
    public static final String FILES_LIST = "Files_List";
    public static final String BACKGROUND_COLOR = "BACKGROUND_COLOR";

    ActivityMainBinding activityMainBinding;
    boolean isIvImportVisible = true;
    boolean isToolbarVisible = true;
    String textFromURI;
    boolean isComingFromWidget;
    Bundle bundle2;
    MainViewModel mainViewModel;
    private FirebaseAnalytics mFirebaseAnalytics;
    SharedPreferences.Editor sharedPrefsEditor;

    @Inject
    TextFileDatabase textFileDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TeleprompterApplication.getAppComponent().inject(this);
        FirebaseCrashlytics.getInstance().log("Crash");

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        isComingFromWidget = intent.getBooleanExtra(IS_FROM_WIDGET, false);
        if (bundle != null) {
            isComingFromWidget = bundle.getBoolean(IS_FROM_WIDGET);
        }

        setSupportActionBar(activityMainBinding.toolbar);
        if (savedInstanceState != null) {
            isToolbarVisible = savedInstanceState.getBoolean(TOOLBAR_VISIBILITY);
            isIvImportVisible = savedInstanceState.getBoolean(IV_IMPORT_VISIBILITY);

            if (!isToolbarVisible) {
                activityMainBinding.toolbar.setVisibility(View.GONE);

            }

            if (!isIvImportVisible) {
                activityMainBinding.ivImport.setVisibility(View.GONE);

            }
        }

        sharedPrefsEditor = getSharedPreferences(DOCUMENT_URI_SELECTED, Context.MODE_PRIVATE).edit();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        bundle2 = new Bundle();
        activityMainBinding.ivImport.setOnClickListener((v) -> openAddFilesMenu());
        activityMainBinding.activityMainBtLoadFiles.setOnClickListener((v -> loadListFromDb()));
    }

    private void loadListFromDb() {
        mainViewModel.getItemList().observe(this, new Observer<List<TextFile>>() {
            @Override
            public void onChanged(List<TextFile> textFiles) {
                DialogFragment newFragment = new SavedListDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(FILES_LIST, (ArrayList<? extends Parcelable>) textFiles);
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "TEST");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.change_speed:
                sendSettingToAnalytics(item);
                startDialogFragment(getString(R.string.change_speed));
                return true;
            case R.id.change_text_size:
                sendSettingToAnalytics(item);
                startDialogFragment(getString(R.string.change_text_size));
                return true;
            case R.id.change_color:
                sendSettingToAnalytics(item);
                changeBackgroundColor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendSettingToAnalytics(MenuItem item) {
        bundle2.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(item.getItemId()));
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle2);
    }

    private void changeBackgroundColor() {
//        ColorPickerDialogBuilder
//                .with(this, R.style.myDialog)
//                .setTitle(getString(R.string.change_color))
//                .initialColor(getColor(R.color.colorPrimary))
//                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                .density(12)
//                .setOnColorSelectedListener(selectedColor -> Toast.makeText(getApplicationContext(), "onColorSelected: 0x" +
//                        Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show())
//                .setPositiveButton(getString(R.string.all_ok), (dialog, selectedColor, allColors) -> {
//                    sharedPrefsEditor.putInt(BACKGROUND_COLOR, selectedColor);
//                })
//                .setNegativeButton(getString(R.string.all_cancel), (dialog, which) -> {
//                })
//                .build()
//                .show();
    }

    private void startDialogFragment(String titleText) {
        DialogFragment speedPickerFragment = new SpeedPickerFragment(titleText);
        speedPickerFragment.show(getSupportFragmentManager(), "Speed");
    }


    private void openAddFilesMenu() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 12
                && resultCode == Activity.RESULT_OK) {
            Uri uriResult1 = resultData.getData();
            if (uriResult1 != null) {
                getContentResolver().takePersistableUriPermission(uriResult1, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            InputStream inputStream = null;
            String str = "";
            StringBuilder buf = new StringBuilder();
            try {
                inputStream = getContentResolver().openInputStream(uriResult1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            if (inputStream != null) {
                try {
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            textFromURI = buf.toString();
            sharedPrefsEditor.apply();
            OpenedDocumentFragment openedDocumentFragment = new OpenedDocumentFragment();
            startFragment(textFromURI, openedDocumentFragment);
            TextFile textFile = new TextFile(getFileName(uriResult1), textFromURI);
            AsyncTask.execute(() -> textFileDatabase.textFileContentDao().insertTextFile(textFile));
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void startFragment(String documentFromURI, OpenedDocumentFragment openedDocumentFragment) {
        activityMainBinding.toolbar.setVisibility(View.GONE);
        activityMainBinding.ivImport.setVisibility(View.GONE);
        isIvImportVisible = false;
        isToolbarVisible = false;
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_LAST_URI, documentFromURI);   //parameters are (key, value).

        openedDocumentFragment.setArguments(bundle);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, openedDocumentFragment, "OP");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IV_IMPORT_VISIBILITY, isIvImportVisible);
        outState.putBoolean(TOOLBAR_VISIBILITY, isToolbarVisible);
        outState.putString(TEXT_DATA, textFromURI);

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
            recreate();
            isIvImportVisible = true;
            isToolbarVisible = true;
            activityMainBinding.ivImport.setVisibility(View.VISIBLE);
            activityMainBinding.toolbar.setVisibility(View.VISIBLE);
        }
    }
}