package com.example.currenciesviewer.model

data class Valute(
    val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val previous: Double
)
