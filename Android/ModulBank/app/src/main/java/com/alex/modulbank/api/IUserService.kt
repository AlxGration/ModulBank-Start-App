package com.alex.modulbank.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface IUserService {

    @GET("api/me")
    fun myInfo(): Call<ResponseBody>

    @GET("api/accounts")
    fun accounts(): Call<ResponseBody>

    @GET("api/newaccount")
    fun newAccount(): Call<ResponseBody>

}