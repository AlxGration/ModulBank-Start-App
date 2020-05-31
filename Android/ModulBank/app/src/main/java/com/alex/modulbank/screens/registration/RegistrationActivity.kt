package com.alex.modulbank.screens.registration

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import javax.inject.Inject


class RegistrationActivity : AppCompatActivity(), RegistrationScreenContract.IView{

    private lateinit var etUsername: EditText
    private lateinit var etEmail:EditText
    private lateinit var etPass1:EditText
    private lateinit var etPass2:EditText
    private lateinit var tvError: TextView
    private lateinit var pBar: ProgressBar
    private lateinit var btnConfirm: Button

    @Inject
    lateinit var regPresenter: RegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        regPresenter.attach(this)
        regPresenter.init()

        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPass1 = findViewById(R.id.et_password)
        etPass2 = findViewById(R.id.et_password_confirm)
        tvError = findViewById(R.id.txt_error)
        pBar = findViewById(R.id.progress_bar)
        btnConfirm = findViewById(R.id.btn_confirm)

        val intent = intent
        val email:String? = intent.getStringExtra("email")
        if (email != null) {
            etEmail.setText(email)
        }
    }

    fun onClickConfirm(view: View) {
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val pass1 = etPass1.text.toString()
        val pass2 = etPass2.text.toString()

        regPresenter.registrate(username, email, pass1, pass2)
    }

    override fun showErrorText(message: String) {
        tvError.text = message
    }

    override fun showNextActivity() {
        val email = etEmail.text.toString()
        val pass = etPass1.text.toString()
        val intent = Intent()
        intent.putExtra("email", email)
        intent.putExtra("pass", pass)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun showProgressBar() {
        btnConfirm.isEnabled = false
        pBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        btnConfirm.isEnabled = true
        pBar.visibility = View.INVISIBLE
    }

    override fun onStop() {
        super.onStop()
        regPresenter.detach()
    }
}
