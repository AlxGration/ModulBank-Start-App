package com.alex.modulbank.di.modules

import com.alex.modulbank.api.IBankOperationsService
import com.alex.modulbank.api.IUserService
import com.alex.modulbank.models.PrefsManager
import com.alex.modulbank.screens.main.frags.account.AccountActionsViewModel
import com.alex.modulbank.screens.main.frags.checking.CheckUserViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountActionsModule {

    @Provides
    @Singleton
    fun provideCheckUserModel(service: IBankOperationsService): AccountActionsViewModel{
        return AccountActionsViewModel(service)
    }
}