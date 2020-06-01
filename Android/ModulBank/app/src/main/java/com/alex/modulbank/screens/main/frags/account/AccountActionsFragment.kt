package com.alex.modulbank.screens.main.frags.account

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alex.modulbank.DTO.Account
import com.alex.modulbank.R
import com.alex.modulbank.di.BaseApplication
import com.alex.modulbank.screens.main.MainActivity
import javax.inject.Inject


class AccountActionsFragment : Fragment(){

    @Inject
    lateinit var viewModel: AccountActionsViewModel
    private lateinit var makeDepoDialog: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        BaseApplication.appComponent.inject(this@AccountActionsFragment)
        val root = inflater.inflate(R.layout.fragment_account_actions, container, false)
        viewModel.attachView(this)

        viewModel.account.value = arguments!!.getSerializable("account") as Account

        val tvBalance = root.findViewById<TextView>(R.id.txt_balance)
        viewModel.account.observe(this, Observer {
            tvBalance.text = it.amount.toString().plus(" p")
        })

        makeDepoDialog = AlertDialog.Builder(root.context)

        val btnMakeDepo = root.findViewById<Button>(R.id.btn_make_depo)
        btnMakeDepo.setOnClickListener{showMakeDepoDialog(root.context) }

        val btnCloseAccount = root.findViewById<Button>(R.id.btn_close_account)
        btnCloseAccount.setOnClickListener{ viewModel.closeAccount() }

        return root
    }

    // setting account number in action bar
    //
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNav()

        val actionBar =(activity as MainActivity).actionBar()
        actionBar.title = "№ ".plus(viewModel.account.value?.number)

        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav()
    }


    // Диалог с суммой пополнения
    //
    private fun showMakeDepoDialog(ctx: Context){
        val etAmount = EditText(ctx)
        etAmount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val txt = s
                var len = txt.length
                val str = txt.split(".")
                // не больше 2ух знаков после точки
                if (str.size == 2 && str[1].length == 3){
                    s.delete(len-1, len)
                }
                // не больше 6 знаков в числе
                len = str[0].length
                if (len > 6){
                    s.delete(len-1, len)
                }
            }
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int,arg2: Int,arg3: Int) {}
            override fun onTextChanged(s: CharSequence,start: Int,before: Int,count: Int) {}
        })


        makeDepoDialog.setTitle("Пополнить счет на сумму:")

        makeDepoDialog.setView(etAmount)
        makeDepoDialog.setPositiveButton("OK") { _,_->
            viewModel.makeDeposit(etAmount.text.toString())
        }
        makeDepoDialog.setNegativeButton("Потом") { _,_->}
        makeDepoDialog.show()
    }

    fun showError(error: String){
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }
    fun closeAccountSuccess(){
        onDestroyView()
    }
}