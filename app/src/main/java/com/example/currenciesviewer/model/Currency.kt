package com.example.currenciesviewer.model

data class Currency(
    val date: String,
    val previousDate: String,
    val valute: List<Valute>
)
