package com.alex.modulbank.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import com.alex.modulbank.screens.main.MainActivity
import com.alex.modulbank.screens.registration.RegistrationActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginScreenContract.IView {

    private lateinit var tvError: TextView
    private lateinit var etEmail:  EditText
    private lateinit var etPass:   EditText
    private lateinit var pBar: ProgressBar
    private lateinit var btnLogin: Button
    private val ACTION_ID = 1

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter.attach(this)
        loginPresenter.init()


        tvError = findViewById(R.id.txt_error)
        etEmail = findViewById(R.id.et_email)
        etPass = findViewById(R.id.et_password)
        pBar = findViewById(R.id.progress_bar)
        btnLogin = findViewById(R.id.btn_login)
    }


    fun onClickLogin(view: View) {
        tvError.text = ""

        val email = etEmail.text.toString()
        val password = etPass.text.toString()

        //showMainActivity()
        // TODO: (un)comment
        loginPresenter.login(email, password)
    }


    fun onClickRegistration(view: View) {
        tvError.text = ""
        val email = etEmail.text.toString()
        val intent = Intent(this, RegistrationActivity::class.java)
        intent.putExtra("email", email)

        startActivityForResult(intent, ACTION_ID)
    }


    override fun showErrorText(message: String) {
        Log.e("TAG", "LOGIN showErrorText  "+message)
        tvError.text = message
    }


    override fun showMainActivity() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

    override fun showProgressBar() {
        btnLogin.isEnabled = false
        pBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        btnLogin.isEnabled = true
        pBar.visibility = View.INVISIBLE
    }

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ACTION_ID && resultCode == Activity.RESULT_OK){
            if (data != null){
                etEmail.setText(data.getStringExtra("email"))
                etPass.setText(data.getStringExtra("pass"))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        loginPresenter.detach()
    }
}
