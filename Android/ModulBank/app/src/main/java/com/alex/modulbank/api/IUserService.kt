package com.alex.modulbank.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IUserService {

    @GET("api/me")
    fun myInfo(): Call<ResponseBody>

    @GET("api/accounts")
    fun accounts(): Call<ResponseBody>

    @GET("api/newaccount")
    fun newAccount(): Call<ResponseBody>

    @GET("api/closeaccount")
    fun closeAccount(@Query("number") number: Long): Call<ResponseBody>

}