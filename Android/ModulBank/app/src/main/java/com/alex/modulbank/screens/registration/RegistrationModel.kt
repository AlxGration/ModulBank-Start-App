package com.alex.modulbank.screens.registration

import android.util.Log
import com.alex.modulbank.DTO.MessageError
import com.alex.modulbank.DTO.UserReg
import com.alex.modulbank.api.IAuthService
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationModel (
    private val authService:IAuthService ){

    private lateinit var presenter: RegistrationScreenContract.IPresenter
    fun attachPresenter(presenter: RegistrationScreenContract.IPresenter){
        this.presenter=presenter
    }


    fun registrationSuccess(){
        presenter.registrationSuccess()
    }
    fun registrationFailed(err: MessageError){
        presenter.registrationFailed(err.errorMessage)
    }

    fun requestRegistration(username: String, email: String, password: String){

        val call: Call<ResponseBody>  = authService.register(UserReg(username, email, password))

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val gson = GsonBuilder().create()
                val json = response.body()!!.string()
                Log.i("TAG", "requestRegistration "+json)

                if (response.isSuccessful) {
                    registrationSuccess()
                } else {
                    if (response.body() != null)
                        registrationFailed(gson.fromJson(json, MessageError::class.java))
                    else registrationFailed(MessageError("error"))
                }
            }

            override  fun onFailure(call: Call<ResponseBody> , t: Throwable) {
                Log.e("Error ", t.message!!)
                registrationFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }
}