package com.alex.modulbank.di.modules

import com.alex.modulbank.models.PrefsManager
import com.alex.modulbank.screens.settings.UserSettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class UserSettingsModule {

    @Provides
    fun provideViewModel(prefs: PrefsManager): UserSettingsPresenter{
        return UserSettingsPresenter(prefs)
    }

}