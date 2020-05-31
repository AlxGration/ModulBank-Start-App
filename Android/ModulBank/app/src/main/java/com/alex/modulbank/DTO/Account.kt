package com.alex.modulbank.DTO

import java.io.Serializable

class Account (val id: String,
               val userId: String,
               val number: Long,
               var amount: Double,
               val status: AccountStatus):Serializable