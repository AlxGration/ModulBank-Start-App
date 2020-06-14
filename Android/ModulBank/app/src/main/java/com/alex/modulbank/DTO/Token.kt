package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")      val token: String,
    @SerializedName("userId")     val userId: String,
    @SerializedName("expiration") val expiration: String
)