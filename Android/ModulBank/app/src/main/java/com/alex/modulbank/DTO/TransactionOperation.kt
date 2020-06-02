package com.alex.modulbank.DTO

import java.math.BigDecimal
import java.util.*

data class TransactionOperation(val id:Int, val number: Long, val amount:BigDecimal, val operDate: Date)