package com.alex.modulbank.api

import com.alex.modulbank.DTO.User
import com.alex.modulbank.DTO.UserReg
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {

// email:    string
// password: string

    @POST("api/login")
    fun login(@Body user: User): Call<ResponseBody>

// username: string
// email:    string
// password: string

    @POST("api/registration")
    fun register(@Body user: UserReg): Call<ResponseBody>

}