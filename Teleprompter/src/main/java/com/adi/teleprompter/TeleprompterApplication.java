package com.adi.teleprompter;

import android.app.Application;

import com.adi.teleprompter.di.AppModule;
import com.adi.teleprompter.di.DaggerTeleprompterAppComponent;
import com.adi.teleprompter.di.DatabaseModule;
import com.adi.teleprompter.di.TeleprompterAppComponent;
import com.google.firebase.FirebaseApp;

public class TeleprompterApplication extends Application {

    private static TeleprompterAppComponent teleprompterAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        teleprompterAppComponent = DaggerTeleprompterAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule())
                .build();
    }


    /**
     * Static component for easy injection through out the application.
     */
    public static TeleprompterAppComponent getAppComponent() {
        return teleprompterAppComponent;
    }
}
