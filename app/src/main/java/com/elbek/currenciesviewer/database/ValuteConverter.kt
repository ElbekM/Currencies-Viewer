package com.elbek.currenciesviewer.database

import androidx.room.TypeConverter
import com.elbek.currenciesviewer.model.entities.ValuteEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class ValuteConverter {
    @TypeConverter
    fun fromMutableList(tasks: MutableList<ValuteEntity>?): String = Gson().toJson(tasks)

    @TypeConverter
    fun toMutableList(data: String): MutableList<ValuteEntity>? =
        Gson().fromJson(data, object : TypeToken<ArrayList<ValuteEntity>>() {}.type)
}
