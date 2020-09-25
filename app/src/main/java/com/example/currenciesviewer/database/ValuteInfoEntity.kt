package com.example.currenciesviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currenciesviewer.model.Valute
import java.util.*

@Entity
data class ValuteInfoEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var date: String,
    var valute: MutableList<ValuteEntity>
)
