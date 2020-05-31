package com.alex.modulbank.screens.main.frags.checking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import com.alex.modulbank.screens.login.LoginActivity
import javax.inject.Inject

class CheckUserFragment : Fragment() {

    @Inject
    lateinit var viewModel: CheckUserViewModel

    private lateinit var tvMessage: TextView
    private lateinit var tvError: TextView
    private lateinit var tvName: TextView
    private lateinit var pBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        BaseApplication.appComponent.inject(this)
        viewModel.attachView(this)

        val root = inflater.inflate(R.layout.fragment_check_user, container, false)


        tvMessage = root!!.findViewById(R.id.txt_message)
        viewModel.message.observe(this, Observer {
            tvMessage.text = it
        })

        tvError = root!!.findViewById(R.id.txt_error)
        viewModel.errorMessage.observe(this, Observer {
            tvError.text = it
        })

        tvName = root!!.findViewById(R.id.txt_name)
        viewModel.name.observe(this, Observer {
            tvName.text = it
        })

        pBar = root!!.findViewById(R.id.progress_bar)
        viewModel.loadingBar.observe(this, Observer {
            pBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        val btnLogout = root!!.findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener{
            viewModel.logout()
        }


        viewModel.requestMyInfo()

        return root
    }
}
