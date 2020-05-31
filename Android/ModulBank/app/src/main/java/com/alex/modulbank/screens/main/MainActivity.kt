package com.alex.modulbank.screens.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import com.alex.modulbank.screens.login.LoginActivity
import com.alex.modulbank.screens.main.frags.checking.CheckUserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var checkViewModel: CheckUserViewModel

    lateinit var navController: NavController
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)

        supportActionBar!!.elevation = 1.5f

        navView = findViewById(R.id.nav_view)
        navView.visibility = View.GONE
        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_check_in,
                R.id.navigation_home,
                R.id.navigation_payments,
                R.id.navigation_chat
            )
        )
        //TODO: clearing backStack. set backStack Len=3
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkViewModel.userChecked.observe(this, Observer {
            if (it) showMainScreen()
        })

        checkViewModel.logout.observe(this, Observer {
            if (it) showLoginActivity()
        })
    }


    private fun showMainScreen(){
        Log.e("TAG", "showMainScreen")
        checkViewModel.userChecked.value = false
        navView.visibility = View.VISIBLE

        navController.popBackStack()
        navController.navigate(R.id.navigation_home)
    }

    private fun showLoginActivity(){
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        checkViewModel.logout.value = false
        finish()
    }

    fun showBottomNav(){
        navView.visibility = View.VISIBLE
    }

    fun hideBottomNav(){
        navView.visibility = View.GONE
    }

    fun actionBar()
            = supportActionBar!!

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            navController.popBackStack()
        return true
    }
}
