package com.alex.modulbank.api

import com.alex.modulbank.DTO.BankTransaction
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IBankOperationsService {

// NumberTo: long 	    | счет получателя
// Amount:   decimal	| сумма

    @POST("api/makedepo")
    fun makeDepo(@Body transaction: BankTransaction): Call<ResponseBody>
}