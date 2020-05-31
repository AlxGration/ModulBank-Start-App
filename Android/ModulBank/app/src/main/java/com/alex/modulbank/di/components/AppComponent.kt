package com.alex.modulbank.di.components

import com.alex.modulbank.di.modules.*
import com.alex.modulbank.screens.main.frags.checking.CheckUserFragment
import com.alex.modulbank.screens.login.LoginActivity
import com.alex.modulbank.screens.main.MainActivity
import com.alex.modulbank.screens.main.frags.account.AccountActionsFragment
import com.alex.modulbank.screens.main.frags.home.HomeFragment
import com.alex.modulbank.screens.registration.RegistrationActivity
import com.alex.modulbank.screens.settings.UserSettingsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules=[
        AppModule::class,
        SharedPrefManagerModule::class,

        LoginScreenModule::class,
        RegistrationScreenModule::class,

        CheckUserFragmentModule::class,
        MainActivityModule::class,
        UserSettingsModule::class,
        AccountActionsModule::class

    ]
)
interface AppComponent{

    fun inject(activity: LoginActivity)
    fun inject(activity: RegistrationActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: UserSettingsActivity)

    fun inject(fragment: CheckUserFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: AccountActionsFragment)
}