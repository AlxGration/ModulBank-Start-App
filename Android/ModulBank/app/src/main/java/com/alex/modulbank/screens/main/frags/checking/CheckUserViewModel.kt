package com.alex.modulbank.screens.main.frags.checking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.modulbank.DTO.MessageError
import com.alex.modulbank.DTO.UserMe
import com.alex.modulbank.DTO.UserStatus
import com.alex.modulbank.api.IUserService
import com.alex.modulbank.models.PrefsManager
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class CheckUserViewModel (private val service: IUserService, private val prefs:PrefsManager): ViewModel() {

    private lateinit var view: CheckUserFragment
    fun attachView(view: CheckUserFragment){
        this.view=view
    }

    //внешние, для MainActivity, чтобы обработать переходы м-у фрагментами
    //
    val userChecked = MutableLiveData<Boolean>().apply { value = false }
    val logout = MutableLiveData<Boolean>().apply { value = false }

    val loadingBar = MutableLiveData<Boolean>().apply { value = false }
    val name = MutableLiveData<String>().apply { value = "" }
    val message = MutableLiveData<String>().apply { value = "" }
    val errorMessage = MutableLiveData<String>().apply { value = "" }

    fun requestMyInfoSuccess(user: UserMe){
        prefs.saveData(user)
        loadingBar.postValue(false)

        if (user.status == UserStatus.VERIFIED) {
            userChecked.postValue(true)
        }else {
            name.postValue(user.username)
            message.postValue("Ожидайте проверку профиля")
        }
    }


    fun requestMyInfoFailed(err: MessageError){
        loadingBar.postValue(false)
        errorMessage.postValue(err.errorMessage)
    }

    fun logout(){
        prefs.clearAuthData()
        logout.postValue(true)
    }

    fun requestMyInfo(){
        loadingBar.postValue(true)
        val call: Call<ResponseBody> = service.myInfo()

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val gson = GsonBuilder().create()
                val json = response.body()!!.string()
                Log.i("TAG", "requestMyInfo "+json)

                if (response.isSuccessful) {

                    val userJson = JSONObject(json)
                    requestMyInfoSuccess(
                        UserMe(
                            userJson.getString("username"),
                            UserStatus.values()[userJson.getInt("status")],
                            userJson.getString("photo")
                        )
                    )
                } else {
                    if (response.body() != null)
                        requestMyInfoFailed(gson.fromJson(json, MessageError::class.java))
                    else requestMyInfoFailed(MessageError("Ошибка!"))
                }
            }

            override  fun onFailure(call: Call<ResponseBody> , t: Throwable) {
                Log.e("Error ", t.message!!)
                requestMyInfoFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }

}