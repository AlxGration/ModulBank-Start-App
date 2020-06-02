package com.alex.modulbank.screens.main.frags.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import com.alex.modulbank.models.AdapterAccountList
import com.alex.modulbank.screens.settings.UserSettingsActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeFragment : Fragment(){

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        BaseApplication.appComponent.inject(this@HomeFragment)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        // Дата
        //
        val sdf = SimpleDateFormat.getDateInstance(DateFormat.LONG)
        val txtTodayDate = root.findViewById<TextView>(R.id.txt_date)
        txtTodayDate.text = sdf.format(Date())


        // Список Счетов
        //
        val accountsListView = root.findViewById<ListView>(R.id.lv_accounts)
        val adapter = AdapterAccountList(root.context, ArrayList(0))
        homeViewModel.accounts.observe(this, Observer {
            adapter.clear()
            adapter.addAll(it)
        })
        accountsListView.adapter = adapter


        // Переход на фрагмет с операциями над счетом
        //
        accountsListView.setOnItemClickListener{parent, view, position, id ->
            val item = adapter.getItem(position)
            val bundle = Bundle()
            bundle.putSerializable("account", item)

            NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(R.id.action_navigation_home_to_account_actions, bundle)
        }


        // Добавить счет
        //
        val btnNewAccount = root.findViewById<Button>(R.id.btn_new_account)
        btnNewAccount.setOnClickListener{
            homeViewModel.newAccount()
        }


        // ProgressBar init
        //
        val pBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        homeViewModel.loadingBar.observe(this, Observer {
            pBar.visibility = if (it) View.VISIBLE else View.GONE
        })


        // Обновить\загрузить список счетов
        //
        homeViewModel.init()
        homeViewModel.accountsListRequest()


        // Открыть настройки
        //
        val btnSettings = root.findViewById<ImageButton>(R.id.img_settings)
        btnSettings.setOnClickListener{
            startActivity(Intent(root.context, UserSettingsActivity::class.java))
        }


        // Показ ошибки
        //
        homeViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        return root
    }
}