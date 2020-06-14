package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName

data class MessageError (
    @SerializedName("errorMessage")
    val errorMessage: String
)