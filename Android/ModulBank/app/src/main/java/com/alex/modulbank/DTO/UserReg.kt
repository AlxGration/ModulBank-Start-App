package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName

data class UserReg(
    @SerializedName("username") val username: String,
    @SerializedName("email")    val email: String,
    @SerializedName("password") val password: String
)