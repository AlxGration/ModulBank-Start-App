package com.alex.modulbank.screens.main.frags.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.modulbank.DTO.Account
import com.alex.modulbank.DTO.AccountStatus
import kotlin.collections.ArrayList

class HomeViewModel(val model: HomeModel): ViewModel(){

    val loadingBar = MutableLiveData<Boolean>()
    val accounts = MutableLiveData<ArrayList<Account>>().apply {
        value = ArrayList()
    }

    fun init(){
        model.attachViewModel(this)
    }

    fun newAccount(){
        model.newAccount()
    }

    fun addAccount(account: Account){
        accounts.value?.add(account)
    }

    fun accountsListRequest(){
        showProgressBar()
        model.getAccounts()
    }

    fun updateAccountsList(accounts: ArrayList<Account>){
        Log.e("TAG", "updateAccountsList")
        this.accounts.value = accounts
        hideProgressBar()
    }

    val errorMessage = MutableLiveData<String>()
    fun accountsReqFailed(message: String) {
        errorMessage.postValue(message)
        hideProgressBar()
    }

    private fun showProgressBar(){
        loadingBar.postValue(true)
    }
    private fun hideProgressBar(){
        loadingBar.postValue(false)
    }

}