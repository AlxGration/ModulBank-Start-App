package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Account (
    @SerializedName("id")     val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("number") val number: Long,
    @SerializedName("amount") var amount: Double,
    @SerializedName("status") val status: AccountStatus
):Serializable