package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class TransactionOperation(
    @SerializedName("id")       val id: Int,
    @SerializedName("number")   val number: Long,
    @SerializedName("amount")   val amount: BigDecimal,
    @SerializedName("operDate") val operDate: Date
)