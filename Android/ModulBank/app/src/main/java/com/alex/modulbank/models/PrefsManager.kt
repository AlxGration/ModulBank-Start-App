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

    private enum class KEYS{
        TOKEN,
        EXPIRATION,
        USER_ID,
        STATUS,
        PHOTO,
        USERNAME,
        EMAIL
    }

    fun checkAuth():Boolean{

        if (sp.getString(KEYS.TOKEN.name, "") == "") return false
//"2020-05-07T02:04:41Z"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateExp: Date = sdf.parse(sp.getString("expiration", ""))
        val dateToday = Date()

        return (dateToday.before(dateExp))
    }


    fun saveData(token: Token){
        spEdit = sp.edit()

        spEdit.putString(KEYS.TOKEN.name, token.token)
        spEdit.putString(KEYS.EXPIRATION.name, token.expiration)
        spEdit.putString(KEYS.USER_ID.name, token.userId)
        spEdit.apply()
    }


    fun saveData(user: UserMe){
        spEdit = sp.edit()

        spEdit.putInt(KEYS.STATUS.name, user.status.ordinal)
        spEdit.putString(KEYS.PHOTO.name, user.photo)
        spEdit.putString(KEYS.USERNAME.name, user.username)
        spEdit.apply()
    }


    fun getAuthData(): Token?{
        if (sp.getString(KEYS.TOKEN.name, "") != "") {

            return Token(
                sp.getString(KEYS.TOKEN.name,"")!!,
                sp.getString(KEYS.USER_ID.name,"")!!,
                sp.getString(KEYS.EXPIRATION.name,"")!!
            )
        }
        return null
    }


    fun getUserInfo():UserMe?{
        if (sp.getString(KEYS.TOKEN.name, "") != "") {

            val index = sp.getInt(KEYS.STATUS.name,-1)

            return UserMe(
                sp.getString(KEYS.USERNAME.name,"")!!,
                UserStatus.values()[index],
                sp.getString(KEYS.PHOTO.name,"")!!,
                sp.getString(KEYS.EMAIL.name, "")!!
            )
        }
        return null
    }

    fun clearAuthData(){
        saveData(Token("", "",""))
    }

    fun saveEmail(email: String){
        spEdit = sp.edit()

        spEdit.putString(KEYS.EMAIL.name, email)
        spEdit.apply()
    }
}