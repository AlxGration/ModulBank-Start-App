package com.alex.modulbank.screens.settings

import com.alex.modulbank.models.PrefsManager
import com.alex.modulbank.screens.BasePresenter

class UserSettingsPresenter(private val prefs: PrefsManager):  BasePresenter<UserSettingsActivity>()  {

    fun init(){
        val userData = prefs.getUserInfo()
        if (userData!= null){
            view?.setName(userData.username)
            view?.setEmail(userData.email)
            view?.setStatus(userData.status.name)
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
        view?.logout()
    }

}