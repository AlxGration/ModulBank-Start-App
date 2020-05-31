package com.alex.modulbank.screens.registration

interface RegistrationScreenContract {
    interface IPresenter{
        fun registrate(username: String, email: String, pass1: String, pass2:String)

        fun registrationSuccess()
        fun registrationFailed(err: String)
        fun init()
    }

    interface IView{
        fun showErrorText(message: String)
        fun showNextActivity()
        fun showProgressBar()
        fun hideProgressBar()
    }
}