package com.alex.modulbank.models

import android.content.Context
import android.content.SharedPreferences
import com.alex.modulbank.DTO.Token
import com.alex.modulbank.DTO.UserMe
import com.alex.modulbank.DTO.UserStatus
import java.text.SimpleDateFormat
import java.util.*


class PrefsManager(private val context:Context) {

    private val SETTINGS = "settings"
    private val sp: SharedPreferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
    private lateinit var spEdit: SharedPreferences.Editor

    fun checkAuth():Boolean{

        if (sp.getString("token", "") == "") return false
//"2020-05-07T02:04:41Z"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateExp: Date = sdf.parse(sp.getString("expiration", ""))
        val dateToday = Date()

        return (dateToday.before(dateExp))
    }


    fun saveData(token: Token){
        spEdit = sp.edit()

        spEdit.putString("token", token.token)
        spEdit.putString("expiration", token.expiration)
        spEdit.putString("userId", token.userId)
        spEdit.apply()
    }


    fun saveData(user: UserMe){
        spEdit = sp.edit()

        spEdit.putInt("status", user.status.ordinal)
        spEdit.putString("photo", user.photo)
        spEdit.putString("username", user.username)
        spEdit.apply()
    }


    fun getAuthData(): Token?{
        if (sp.getString("token", "") != "") {

            return Token(
                sp.getString("token","")!!,
                sp.getString("userId","")!!,
                sp.getString("expiration","")!!
            )
        }
        return null
    }


    fun getUserInfo():UserMe?{
        if (sp.getString("token", "") != "") {

            val index = sp.getInt("status",-1)

            return UserMe(
                sp.getString("username","")!!,
                UserStatus.values()[index],
                sp.getString("photo","")!!,
                sp.getString("email", "")!!
            )
        }
        return null
    }

    fun clearAuthData(){
        saveData(Token("", "",""))
    }

    fun saveEmail(email: String){
        spEdit = sp.edit()

        spEdit.putString("email", email)
        spEdit.apply()
    }


}