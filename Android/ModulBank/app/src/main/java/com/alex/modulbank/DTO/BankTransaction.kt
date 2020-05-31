package com.alex.modulbank.DTO

import java.math.BigDecimal

data class BankTransaction (val NumberFrom: Long = -1, val NumberTo: Long, val Amount: BigDecimal)
