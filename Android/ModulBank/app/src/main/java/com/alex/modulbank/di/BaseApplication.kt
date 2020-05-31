package com.alex.modulbank.di

import android.app.Application
import com.alex.modulbank.di.components.AppComponent
import com.alex.modulbank.di.components.DaggerAppComponent
import com.alex.modulbank.di.modules.*

class BaseApplication: Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }
    private fun initDagger(){
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .sharedPrefManagerModule(SharedPrefManagerModule(this))
            .loginScreenModule(LoginScreenModule())
            .registrationScreenModule(RegistrationScreenModule())
            .mainActivityModule(MainActivityModule())
            .build()
    }
}