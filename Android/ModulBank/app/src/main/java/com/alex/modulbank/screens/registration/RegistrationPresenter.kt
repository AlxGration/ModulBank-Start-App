package com.alex.modulbank.screens.registration

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.alex.modulbank.screens.BasePresenter
import javax.inject.Inject


class RegistrationPresenter @Inject constructor(): BasePresenter<RegistrationScreenContract.IView>(),
    RegistrationScreenContract.IPresenter {

    @Inject
    lateinit var model: RegistrationModel

    override fun registrate(username: String, email: String, pass1: String, pass2:String){

        view?.showProgressBar()

        if (username.length < 3){
            registrationFailed("Имя меньше 3-х символов.")
            return
        }

        if ( ! isValidEmail(email)){
            registrationFailed("Некорректный email")
            return
        }

        if (pass1.length < 5 || pass2.length < 5) {
            registrationFailed("Пароль меньше 6-ти символов.")
            return
        }

        if (pass1 != pass2) {
            registrationFailed("Пароли не совпадают")
            return
        }

        model.requestRegistration(username, email, pass1)
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    override fun init() {
        model.attachPresenter(this)
    }

    override fun registrationSuccess(){
        view?.hideProgressBar()
        Log.e("TAG", "PRESENTER registrationSuccess")
        view?.showNextActivity()
    }
    override fun registrationFailed(err: String){
        view?.hideProgressBar()
        Log.e("TAG", "PRESENTER registrationFailed " +err+" "+(view==null))
        view?.showErrorText(err)
    }
}