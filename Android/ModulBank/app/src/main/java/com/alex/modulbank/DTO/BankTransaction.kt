package com.alex.modulbank.DTO

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BankTransaction (
    @SerializedName("NumberFrom") val NumberFrom: Long = -1,
    @SerializedName("NumberTo")   val NumberTo: Long,
    @SerializedName("Amount")     val Amount: BigDecimal
)
