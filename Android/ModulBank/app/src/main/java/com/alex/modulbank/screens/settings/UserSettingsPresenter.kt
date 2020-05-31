package com.alex.modulbank.screens.settings

import com.alex.modulbank.models.PrefsManager

class UserSettingsPresenter(private val prefs: PrefsManager) {

    private lateinit var view: UserSettingsActivity

    fun attachView(view: UserSettingsActivity){
        this.view = view
    }

    fun init(){
        val userData = prefs.getUserInfo()
        if (userData!= null){
            view.setName(userData.username)
            view.setEmail(userData.email)
            view.setStatus(userData.status.name)
            //TODO: download image
        }
    }

    fun delUser(){
        //TODO: delUser
    }

    fun changePassword(){
        //TODO: changePassword
    }

    fun addPhoto(){
        //TODO: addPhoto
    }

    fun logout(){
        prefs.clearAuthData()
        view.logout()
    }

}