package com.alex.modulbank.screens.main.frags.home

import android.util.Log
import com.alex.modulbank.DTO.Account
import com.alex.modulbank.DTO.AccountStatus
import com.alex.modulbank.DTO.MessageError
import com.alex.modulbank.api.IUserService
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeModel (var userService: IUserService){

    private lateinit var viewModel: HomeViewModel
    fun attachViewModel(viewModel: HomeViewModel){
        this.viewModel=viewModel
    }

    fun requestSuccess(accounts: ArrayList<Account>){
        Log.e("TAG", "MODEL requestSuccess "+accounts.size)
        viewModel.updateAccountsList(accounts)
    }
    fun requestFailed(err: MessageError){
        Log.e("TAG", "MODEL requestFailed "+err.errorMessage)
        viewModel.accountsReqFailed(err.errorMessage)
    }


    fun getAccounts(){

        val call: Call<ResponseBody> = userService.accounts()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val gson = GsonBuilder().create()

                if (response.isSuccessful) {
                    requestSuccess(
                            toList(response.body()!!.string())
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

    fun toList(json: String): ArrayList<Account> {
        Log.e("TAG", "json array : "+json)

        val array = JSONArray(json)
        val accountsList = ArrayList<Account>(array.length())
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            accountsList.add(
                Account(
                    item.getString("id"),
                    item.getString("user_id"),
                    item.getLong("number"),
                    item.getDouble("amount"),
                    if (item.getInt("status") == 0) AccountStatus.OPENED
                    else AccountStatus.CLOSED
                )
            )
        }
        return accountsList
    }

    fun newAccount(){

        val call: Call<ResponseBody> = userService.newAccount()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val gson = GsonBuilder().create()

                if (response.isSuccessful) {
                    viewModel.addAccount(
                        gson.fromJson(response.body().toString(), Account::class.java)
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
}