package com.example.currenciesviewer.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ValuteInfoEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var date: String,
    var valute: MutableList<ValuteEntity>
)
