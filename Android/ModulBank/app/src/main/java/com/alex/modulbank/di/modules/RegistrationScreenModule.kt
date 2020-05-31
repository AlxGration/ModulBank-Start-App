package com.alex.modulbank.di.modules

import com.alex.modulbank.api.IAuthService
import com.alex.modulbank.screens.registration.RegistrationScreenContract
import com.alex.modulbank.screens.registration.RegistrationModel
import com.alex.modulbank.screens.registration.RegistrationPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RegistrationScreenModule {

    @Provides
    @Singleton
    fun providePresenter(): RegistrationScreenContract.IPresenter =
        RegistrationPresenter()

    @Provides
    @Singleton
    fun provideModel(service: IAuthService): RegistrationModel {
        return RegistrationModel(service)
    }

}