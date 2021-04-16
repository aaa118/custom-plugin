package com.adi.teleprompter.di;

import com.adi.teleprompter.views.MainActivity;
import com.adi.teleprompter.views.dialogs.SpeedPickerFragment;
import com.adi.teleprompter.views.viewmodels.MainViewModel;

import dagger.Component;

@Component(modules = {AppModule.class, DatabaseModule.class})
public interface TeleprompterAppComponent {
    void inject (MainActivity mainActivity);
    void inject (MainViewModel mainViewModel);
    void inject(SpeedPickerFragment speedPickerFragment);
}
