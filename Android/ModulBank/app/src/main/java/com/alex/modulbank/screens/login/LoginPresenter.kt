package com.alex.modulbank.screens.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.alex.modulbank.screens.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(): BasePresenter<LoginScreenContract.IView>(),
    LoginScreenContract.IPresenter {

    @Inject
    lateinit var model: LoginModel


    override fun init(){
        model.attachPresenter(this)

        // auth checking
        if (model.checkAuth())
            view?.showMainActivity()

        Log.e("TAG", "PRESENTER INIT")
    }

    override fun login(email: String, password: String){
        view?.showProgressBar()
        Log.e("TAG", "PRESENTER login")

        if (password.length < 5 ) {//loginFailed
            loginFailed("Пароль меньше 6-ти символов.")
            return
        }
        if(!isValidEmail(email)) {
            loginFailed("Некорректный email.")
            return
        }

        model.requestLogin(email, password)
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    override fun loginSuccess(){
        view?.hideProgressBar()
        Log.e("TAG", "PRESENTER loginSuccess")
        view?.showMainActivity()
    }
    override fun loginFailed(err: String){
        view?.hideProgressBar()
        Log.e("TAG", "PRESENTER loginFailed "+err+" "+(view==null))
        view?.showErrorText(err)
    }


}