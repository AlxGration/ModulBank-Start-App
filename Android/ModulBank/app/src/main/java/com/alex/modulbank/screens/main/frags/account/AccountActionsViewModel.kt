package com.alex.modulbank.screens.main.frags.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.modulbank.DTO.Account
import com.alex.modulbank.DTO.BankTransaction
import com.alex.modulbank.DTO.MessageError
import com.alex.modulbank.DTO.TransactionOperation
import com.alex.modulbank.api.IBankOperationsService
import com.alex.modulbank.api.IUserService
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountActionsViewModel (private val bankService: IBankOperationsService, private val userService: IUserService): ViewModel() {

    val account = MutableLiveData<Account>()
    val loadingProcess = MutableLiveData<Boolean>()
    val transactionsList = MutableLiveData<ArrayList<TransactionOperation>>()
    val errorText = MutableLiveData<String>()
    val closeAccountSuccess = MutableLiveData<Boolean>()

    private var lastOperationAmount: Double = 0.0


    fun requestFailed(messageError: MessageError){
        loadingProcess.postValue(false)
        errorText.postValue(messageError.errorMessage)
    }

    fun makeDeposit(txtAmount: String){
        if (txtAmount.isEmpty()) return
        if (account.value == null) return

        loadingProcess.postValue(true)
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
                    loadingProcess.postValue(false)
                    addTransactionToList(
                        TransactionOperation(-1, -1, BigDecimal(lastOperationAmount), Date())
                    )
                } else {
                    if (response.body() != null)
                        requestFailed(gson.fromJson(response.body()!!.string(), MessageError::class.java))
                    else requestFailed(MessageError("Ошибка!"))
                }
            }
            override  fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Error ", t.message!!)
                requestFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }

    fun closeAccount(){
        loadingProcess.postValue(true)

        userService.closeAccount(account.value!!.number)
            .enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val gson = GsonBuilder().create()

                if (response.isSuccessful) {
                    loadingProcess.postValue(false)
                    closeAccountSuccess.postValue(true)
                } else {
                    if (response.body() != null)
                        requestFailed(gson.fromJson(response.body()!!.string(), MessageError::class.java))
                    else requestFailed(MessageError("Ошибка!"))
                }
            }
            override  fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Error ", t.message!!)
                requestFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }

    fun transactionsListRequest(){
        loadingProcess.postValue(true)

        val call: Call<ResponseBody> = bankService.transactionsList(account.value!!.number)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val json = response.body()!!.string()
                val gson = GsonBuilder().create()

                if (response.isSuccessful) {
                    loadingProcess.postValue(false)
                    transactionsList.value = toList(json)
                } else {
                    if (response.body() != null)
                        requestFailed(gson.fromJson(json, MessageError::class.java))
                    else requestFailed(MessageError("Ошибка!"))
                }
            }
            override  fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Error ", t.message!!)
                requestFailed(MessageError( "Нет подключения к серверу" ))
            }
        })
    }

    fun toList(json: String): ArrayList<TransactionOperation> {
        Log.e("TAG", "json array : "+json)

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val array = JSONArray(json)
        val transactions = ArrayList<TransactionOperation>(array.length())
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            transactions.add(
                TransactionOperation(
                    item.getInt("id"),
                    item.getLong("number"),
                    BigDecimal(item.getLong("amount")),
                    sdf.parse(item.getString("oper_date"))
                )
            )
        }
        return transactions
    }

    private fun addTransactionToList(tr: TransactionOperation){

    }
}