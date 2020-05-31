package com.alex.modulbank.screens.login

import android.util.Log
import com.alex.modulbank.DTO.MessageError
import com.alex.modulbank.DTO.Token
import com.alex.modulbank.DTO.User
import com.alex.modulbank.api.IAuthService
import com.alex.modulbank.models.PrefsManager
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginModel (private val prefs: PrefsManager, private val authService:IAuthService){


    private lateinit var presenter: LoginScreenContract.IPresenter
    fun attachPresenter(presenter: LoginScreenContract.IPresenter){
        this.presenter=presenter
    }

    fun checkAuth(): Boolean
            = prefs.checkAuth()


    fun loginSuccess(email: String, token: Token){
        prefs.saveData(token)
        prefs.saveEmail(email)
        presenter.loginSuccess()
    }


    fun loginFailed(err: MessageError){
        presenter.loginFailed(err.errorMessage)
    }


    fun requestLogin(email: String, password: String){

        val call: Call<ResponseBody>  = authService.login(User(email, password))

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val gson = GsonBuilder().create()
                val json = response.body()!!.string()
                Log.i("TAG", "requestLogin "+json)

                if (response.isSuccessful) {
                    loginSuccess(email, gson.fromJson(json, Token::class.java))
                } else {
                    if (response.body() != null)
                        loginFailed(gson.fromJson(json, MessageError::class.java))
                    else loginFailed(MessageError("error"))
                }
            }
            override  fun onFailure(call: Call<ResponseBody> , t: Throwable) {
                Log.e("Error ", t.message!!)
                loginFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }
}