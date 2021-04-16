package com.adi.teleprompter.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application appContext;

    public AppModule (Application context) {
        this.appContext = context;
    }

    @Provides
    Context provideContext() {
        return appContext;
    }

}
