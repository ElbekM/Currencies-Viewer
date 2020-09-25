package com.example.currenciesviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currenciesviewer.model.Valute

@Entity
data class ValuteEntity(
    @PrimaryKey val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: String,
    val name: String,
    val value: String,
    val delta: String,
    val deltaUp: Boolean
) {
    constructor(valutes: Valute) : this(
        id = valutes.id,
        numCode = valutes.numCode,
        charCode = valutes.charCode,
        nominal = valutes.nominal,
        name = valutes.name,
        value = valutes.value,
        delta = valutes.delta,
        deltaUp = valutes.deltaUp
    )

    companion object {
        fun createList(valutes: List<Valute>): List<ValuteEntity> =
            valutes.map { ValuteEntity(it) }
    }
}

fun List<ValuteEntity>.toModel(): List<Valute> =
    map {
        Valute(
            id = it.id,
            numCode = it.numCode,
            charCode = it.charCode,
            nominal = it.nominal,
            name = it.name,
            value = it.value,
            delta = it.delta,
            deltaUp = it.deltaUp
        )
    }.toMutableList()
