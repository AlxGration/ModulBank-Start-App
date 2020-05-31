package com.alex.modulbank.di.modules

import com.alex.modulbank.api.IAuthService
import com.alex.modulbank.screens.login.LoginScreenContract
import com.alex.modulbank.screens.login.LoginModel
import com.alex.modulbank.models.PrefsManager
import com.alex.modulbank.screens.login.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class LoginScreenModule {

    @Provides
    fun providePresenter(): LoginScreenContract.IPresenter =
        LoginPresenter()

    @Provides
    fun provideModel(prefs: PrefsManager,
                     service: IAuthService): LoginModel {
        return LoginModel(prefs, service)
    }


}

