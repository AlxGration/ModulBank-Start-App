package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName

data class UserMe (
    @SerializedName("username") val username: String,
    @SerializedName("status")   val status: UserStatus,
    @SerializedName("photo")    val photo: String,
    @SerializedName("email")    var email: String=""
)