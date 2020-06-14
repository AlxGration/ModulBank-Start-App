package com.alex.modulbank.screens.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import com.alex.modulbank.screens.login.LoginActivity
import javax.inject.Inject


class UserSettingsActivity : AppCompatActivity() {


    @Inject
    lateinit var presenter: UserSettingsPresenter

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvStatus: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.appComponent.inject(this)
        setContentView(R.layout.activity_user_settings)


        tvName = findViewById(R.id.tv_name)
        tvEmail = findViewById(R.id.tv_email)
        tvStatus = findViewById(R.id.tv_status)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        presenter.init()
    }

    fun onClickDelUser(view: View) {presenter.delUser() }
    fun onClickChangePassword(view: View) {presenter.changePassword()}
    fun onClickAddPhoto(view: View) {presenter.addPhoto()}
    fun onClickLogout(view: View) {presenter.logout()}

    fun setName(name: String){tvName.text = "Имя:      "+name}
    fun setEmail(email: String){tvEmail.text = "Email:    "+email}
    fun setStatus(status: String){tvStatus.text = "Статус:  "+status}
    fun logout(){
        var intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }
}
