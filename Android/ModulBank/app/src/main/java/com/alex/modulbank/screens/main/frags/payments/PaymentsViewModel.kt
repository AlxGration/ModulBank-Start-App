package com.alex.modulbank.screens.main.frags.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Платежи и переводы Fragment"
    }
    val text: LiveData<String> = _text
}