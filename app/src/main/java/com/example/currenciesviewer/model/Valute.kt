package com.example.currenciesviewer.model

data class Valute(
    val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: String,
    val name: String,
    val value: String,
    val delta: String,
    val deltaUp: Boolean
)
