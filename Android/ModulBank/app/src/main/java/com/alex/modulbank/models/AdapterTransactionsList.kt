package com.alex.modulbank.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.alex.modulbank.DTO.TransactionOperation
import com.alex.modulbank.R
import java.math.BigDecimal
import java.text.SimpleDateFormat

class AdapterTransactionsList constructor(val ctx: Context, val transactions: List<TransactionOperation>): ArrayAdapter<TransactionOperation>(ctx, R.layout.li_transaction, transactions) {

    private val inflater: LayoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: inflater.inflate(R.layout.li_transaction, parent, false)
        val transaction: TransactionOperation = transactions[position]

        val txtAmount = view.findViewById<TextView>(R.id.txt_amount)
        txtAmount.text = transaction.amount.toString().plus(" p")
        txtAmount.setTextColor(
            if (transaction.amount < BigDecimal.ZERO) ctx.resources.getColor(R.color.colorError)
            else ctx.resources.getColor(R.color.colorAccent)
        )
        view.findViewById<TextView>(R.id.txt_date).text = sdf.format(transaction.operDate)

        return view
    }

}