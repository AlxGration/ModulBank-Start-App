package com.alex.modulbank.screens.main.frags.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.modulbank.DTO.Account
import com.alex.modulbank.DTO.BankTransaction
import com.alex.modulbank.DTO.MessageError
import com.alex.modulbank.api.IBankOperationsService
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class AccountActionsViewModel (private val bankService: IBankOperationsService): ViewModel() {

    val account = MutableLiveData<Account>()

    private var lastOperationAmount: Double = 0.0
    private lateinit var view: AccountActionsFragment


    fun attachView(view: AccountActionsFragment){
        this.view = view
    }

    fun requestFailed(messageError: MessageError){
        view.showError(messageError.errorMessage)
    }

    fun makeDeposit(txtAmount: String){
        if (txtAmount.isEmpty()) return
        if (account.value == null) return

        lastOperationAmount = txtAmount.toDouble()

        bankService.makeDepo(
            BankTransaction(
                NumberTo = account.value!!.number,
                Amount = BigDecimal(txtAmount)
            )
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val gson = GsonBuilder().create()

                if (response.isSuccessful) {
                    if (account.value != null)
                        account.value = account.value?.also{ it-> it.amount += lastOperationAmount}
                } else {
                    if (response.body() != null)
                        requestFailed(gson.fromJson(response.body()!!.string(), MessageError::class.java))
                    else requestFailed(MessageError("error"))
                }
            }

            override  fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Error ", t.message!!)
                requestFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }

}