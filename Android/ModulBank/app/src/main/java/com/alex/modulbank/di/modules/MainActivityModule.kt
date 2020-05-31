package com.alex.modulbank.di.modules

import com.alex.modulbank.api.IUserService
import com.alex.modulbank.screens.main.frags.home.HomeModel
import com.alex.modulbank.screens.main.frags.home.HomeViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {

    @Provides
    @Singleton
    fun provideModel(service: IUserService): HomeModel{
        return HomeModel(service)
    }

    @Provides
    @Singleton
    fun provideHomeViewModel(model: HomeModel): HomeViewModel{
        return HomeViewModel(model)
    }
}