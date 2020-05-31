package com.alex.modulbank.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.alex.modulbank.DTO.Account
import com.alex.modulbank.R

class AdapterAccountList constructor(val ctx: Context, val accounts: List<Account>): ArrayAdapter<Account>(ctx, R.layout.li_account, accounts) {

    private val inflater: LayoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View
        val account: Account = accounts[position]


        if(convertView == null){
            view = inflater.inflate(R.layout.li_account, parent, false)
            view.findViewById<ImageView>(R.id.img_ruble).setImageResource(R.drawable.ic_ruble)
        } else {
            view = convertView
        }

        view.findViewById<TextView>(R.id.txt_number).text = "№ "+ account.number
        view.findViewById<TextView>(R.id.txt_amount).text = account.amount.toString()


        return view
    }

}