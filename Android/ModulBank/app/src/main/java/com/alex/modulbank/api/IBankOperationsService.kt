package com.alex.modulbank.api

import com.alex.modulbank.DTO.BankTransaction
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IBankOperationsService {

// NumberTo: long 	    | счет получателя
// Amount:   decimal	| сумма

    @POST("api/makedepo")
    fun makeDepo(@Body transaction: BankTransaction): Call<ResponseBody>

// number:   long       | номер счета
    @GET("api/transactions")
    fun transactionsList(@Query("number") number: Long): Call<ResponseBody>
}