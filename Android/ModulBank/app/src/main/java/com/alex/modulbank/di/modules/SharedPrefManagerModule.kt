package com.alex.modulbank.di.modules

import android.content.Context
import com.alex.modulbank.models.PrefsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefManagerModule (private val context: Context) {

    @Provides
    @Singleton
    fun providesSharedPrefManager(): PrefsManager{
        return PrefsManager(context)
    }
}