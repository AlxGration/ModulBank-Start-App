package com.alex.modulbank.screens.login

interface LoginScreenContract {

    interface IPresenter{
    	fun login(email: String, password: String)
    	fun init()
        fun loginSuccess()
        fun loginFailed(err: String)
    }

    interface IView{
    	fun showErrorText(message: String)
    	fun showMainActivity()
        fun showProgressBar()
        fun hideProgressBar()
    }
}